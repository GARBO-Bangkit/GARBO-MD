package com.garbo.garboapplication.view.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.garbo.garboapplication.Result
import com.garbo.garboapplication.databinding.FragmentHistoryBinding
import com.garbo.garboapplication.view.login.LoginActivity

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val historyViewModel: HistoryViewModel by activityViewModels()
    private var token = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        token = arguments?.getString(HistoryActivity.TOKEN_KEY).toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getHistories(token)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // To avoid memory leaks
    }

    private fun getHistories(token: String) {
        historyViewModel.getHistories(token).observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val data = result.data

                        viewManager = LinearLayoutManager(context)
                        viewAdapter = HistoryListAdapter(data.historyResponse!!)

                        with(binding.rvHistories) {
                            setHasFixedSize(true)
                            layoutManager = viewManager
                            adapter = viewAdapter
                        }
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE

                        val statusCode = result.error.let { message ->
                            Regex("HTTP (\\d+)").find(message)?.groups?.get(1)?.value
                        }

                        when (statusCode) {
                            "401" -> {
                                val message = "Token has expired"
                                AlertDialog.Builder(requireContext()).apply {
                                    setTitle("Selamat!")
                                    setMessage(message)
                                    setPositiveButton("Lanjut") { _, _ ->
                                        val intent =
                                            Intent(context, LoginActivity::class.java)
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent)
                                        activity?.finish()
                                    }
                                    create()
                                    show()
                                }
                            }

                            else -> {
                                AlertDialog.Builder(requireContext()).apply {
                                    setTitle("Error")
                                    setMessage("Terjadi kesalahan\n" + result.error)
                                    setPositiveButton("Refresh") { _, _ ->
                                        getHistories(token)
                                    }
                                    setNegativeButton("Cancel") { dialog, _ ->
                                        dialog.dismiss()
                                    }
                                    create()
                                    show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}