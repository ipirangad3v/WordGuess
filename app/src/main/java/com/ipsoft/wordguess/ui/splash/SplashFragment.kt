package com.ipsoft.wordguess.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ipsoft.wordguess.BuildConfig
import com.ipsoft.wordguess.R
import com.ipsoft.wordguess.databinding.SplashFragmentBinding
import com.ipsoft.wordguess.domain.core.constants.SPLASH_TIME
import com.ipsoft.wordguess.domain.core.extension.delayOnLifeCycle
import com.ipsoft.wordguess.domain.core.extension.navTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: SplashFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SplashFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.delayOnLifeCycle(SPLASH_TIME) {

            navTo(R.id.action_splashFragment_to_mainFragment)

        }
        binding.txvVersion.text = getString(R.string.version, BuildConfig.VERSION_NAME)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}