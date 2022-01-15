package com.ipsoft.wordguess.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ipsoft.wordguess.R
import com.ipsoft.wordguess.data.entities.request.WordRequest
import com.ipsoft.wordguess.databinding.MainFragmentBinding
import com.ipsoft.wordguess.domain.core.exception.Failure
import com.ipsoft.wordguess.domain.core.extension.failure
import com.ipsoft.wordguess.domain.core.extension.navTo
import com.ipsoft.wordguess.domain.core.extension.observe
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainFragment : Fragment(), View.OnClickListener {

    private var _binding: MainFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        setListeners()
        setKeyboardListeners()
    }

    private fun setKeyboardListeners() {
        binding.ctlKeyboard.a.setOnClickListener(this)
        binding.ctlKeyboard.b.setOnClickListener(this)
        binding.ctlKeyboard.c.setOnClickListener(this)
        binding.ctlKeyboard.d.setOnClickListener(this)
        binding.ctlKeyboard.e.setOnClickListener(this)
        binding.ctlKeyboard.f.setOnClickListener(this)
        binding.ctlKeyboard.g.setOnClickListener(this)
        binding.ctlKeyboard.h.setOnClickListener(this)
        binding.ctlKeyboard.i.setOnClickListener(this)
        binding.ctlKeyboard.j.setOnClickListener(this)
        binding.ctlKeyboard.k.setOnClickListener(this)
        binding.ctlKeyboard.l.setOnClickListener(this)
        binding.ctlKeyboard.m.setOnClickListener(this)
        binding.ctlKeyboard.n.setOnClickListener(this)
        binding.ctlKeyboard.o.setOnClickListener(this)
        binding.ctlKeyboard.p.setOnClickListener(this)
        binding.ctlKeyboard.q.setOnClickListener(this)
        binding.ctlKeyboard.r.setOnClickListener(this)
        binding.ctlKeyboard.s.setOnClickListener(this)
        binding.ctlKeyboard.t.setOnClickListener(this)
        binding.ctlKeyboard.u.setOnClickListener(this)
        binding.ctlKeyboard.v.setOnClickListener(this)
        binding.ctlKeyboard.x.setOnClickListener(this)
        binding.ctlKeyboard.y.setOnClickListener(this)
        binding.ctlKeyboard.w.setOnClickListener(this)
        binding.ctlKeyboard.z.setOnClickListener(this)
        binding.ctlKeyboard.del.setOnClickListener(this)
        binding.ctlKeyboard.enter.setOnClickListener(this)
    }

    private fun setObservers() {
        with(viewModel) {
            observe(word) {
                it?.let {
                    handleWordFetch(it)
                }
            }
            observe(loading) {
                it?.let {
                    loading(it)
                }
            }
            failure(failure) {
                it?.let {
                    fail(it)
                }
            }
            getRandomWord(WordRequest())
        }
    }

    private fun setListeners() {
        binding.btnRefresh.setOnClickListener {
            viewModel.getRandomWord(WordRequest())
        }
        binding.imvHelp.setOnClickListener {
            navTo(R.id.action_mainFragment_to_helpFragment)
        }
    }


    private fun fail(failure: Failure) {
        Timber.i("----- $failure")
        Toast.makeText(requireContext(), R.string.fail_fetch_word, Toast.LENGTH_SHORT).show()
    }

    private fun handleWordFetch(word: String) {
        Toast.makeText(requireContext(), word, Toast.LENGTH_LONG).show()
        resetBoard(word)
    }

    private fun resetBoard(word: String) {

    }

    private fun loading(loading: Boolean) {

        Timber.i("----- loading $loading")

        val progressBar = activity?.findViewById<ProgressBar>(R.id.pgb_loading)

        when (loading) {
            true -> progressBar?.visibility = View.VISIBLE
            else -> progressBar?.visibility = View.GONE
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(view: View) {

        val stringId = if (view.id == View.NO_ID) "no-id";
        else view.resources.getResourceName(view.id);

        when (stringId) {
            in "a".."z" -> {
                Timber.i("----- $stringId")
            }
            "del" -> {
                Timber.i("----- $stringId")
            }
            "enter" -> {
                Timber.i("----- $stringId")
            }
            "no-id" -> {
                Timber.i("----- $stringId")
            }
        }
    }
}