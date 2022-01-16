package com.ipsoft.wordguess.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ipsoft.wordguess.R
import com.ipsoft.wordguess.data.entities.request.WordRequest
import com.ipsoft.wordguess.databinding.MainFragmentBinding
import com.ipsoft.wordguess.databinding.RowWordBinding
import com.ipsoft.wordguess.domain.core.exception.Failure
import com.ipsoft.wordguess.domain.core.extension.*
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class MainFragment : Fragment(), View.OnClickListener {

    private var _binding: MainFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private var guessingWord: String = ""
    private var guessingTry = 1
    private lateinit var rowOfLetter: MutableList<TextView>
    private lateinit var rowOfLetterFields: MutableList<ConstraintLayout>
    private lateinit var keyboardRow: MutableList<ConstraintLayout>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveListOfLetterOfKeyboard()
        setObservers()
        setListeners()
        setKeyboardListeners()
        selectRow()

    }

    private fun saveListOfLetterOfKeyboard() {
        keyboardRow = mutableListOf(
            binding.ctlKeyboard.a,
            binding.ctlKeyboard.b,
            binding.ctlKeyboard.c,
            binding.ctlKeyboard.d,
            binding.ctlKeyboard.e,
            binding.ctlKeyboard.f,
            binding.ctlKeyboard.g,
            binding.ctlKeyboard.h,
            binding.ctlKeyboard.i,
            binding.ctlKeyboard.j,
            binding.ctlKeyboard.k,
            binding.ctlKeyboard.l,
            binding.ctlKeyboard.m,
            binding.ctlKeyboard.n,
            binding.ctlKeyboard.o,
            binding.ctlKeyboard.p,
            binding.ctlKeyboard.q,
            binding.ctlKeyboard.r,
            binding.ctlKeyboard.s,
            binding.ctlKeyboard.t,
            binding.ctlKeyboard.u,
            binding.ctlKeyboard.v,
            binding.ctlKeyboard.x,
            binding.ctlKeyboard.y,
            binding.ctlKeyboard.w,
            binding.ctlKeyboard.z
        )
    }

    private fun selectRow() {
        when (guessingTry) {
            1 -> {
                useRow(binding.ctlBoard.row1)
            }
            2 -> {
                useRow(binding.ctlBoard.row2)
            }
            3 -> {
                useRow(binding.ctlBoard.row3)
            }
            4 -> {
                useRow(binding.ctlBoard.row4)
            }
            5 -> {
                useRow(binding.ctlBoard.row5)
            }
            6 -> {
                useRow(binding.ctlBoard.row6)
            }
        }
    }

    private fun useRow(row: RowWordBinding) {

        rowOfLetter = mutableListOf(
            row.txvLetter1,
            row.txvLetter2,
            row.txvLetter3,
            row.txvLetter4,
            row.txvLetter5
        )
        rowOfLetterFields = mutableListOf(
            row.ctlLetterBox1,
            row.ctlLetterBox2,
            row.ctlLetterBox3,
            row.ctlLetterBox4,
            row.ctlLetterBox5,
        )
    }

    private fun updateRow() {

        clearRow()
        if (guessingWord.isNotEmpty()) {
            for (index in guessingWord.indices) {
                rowOfLetter[index].text = guessingWord[index].toString()
            }

        }


    }

    private fun clearRow() {
        for (index in rowOfLetter.indices) {
            rowOfLetter[index].text = ""
        }
    }

    private fun setKeyboardListeners() {
        keyboardRow.forEach {
            it.setOnClickListener(this)
        }
        binding.ctlKeyboard.del.setOnClickListener(this)
        binding.ctlKeyboard.enter.setOnClickListener(this)
    }

    private fun setObservers() {
        with(viewModel) {
            observe(word) {
                it?.let {
                    handleWordFetch()
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
//        binding.imvHelp.setOnClickListener {
//            navTo(R.id.action_mainFragment_to_helpFragment)
//        }
    }


    private fun fail(failure: Failure) {
        Timber.i("----- $failure")
        Toast.makeText(requireContext(), R.string.fail_fetch_word, Toast.LENGTH_SHORT).show()
    }

    private fun handleWordFetch() {
        Toast.makeText(requireContext(), R.string.new_game_started, Toast.LENGTH_SHORT).show()
        resetGame()
    }

    private fun resetGame() {

        guessingWord = ""
        resetBoard()
        resetKeyboard()

    }

    private fun resetBoard() {
        for (row in 1..6) {
            guessingTry = row
            selectRow()
            for (index in rowOfLetter.indices) {
                rowOfLetter[index].text = ""
                rowOfLetterFields[index].setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.empty_space_background)
                )

            }
        }
        guessingTry = 1
        selectRow()
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

        val stringId = view.getNamedId()

        Timber.i("----- $stringId")

        if (stringId.length == 1 && stringId.lowercase(Locale.getDefault()) in "a".."z") updateGuessingWord(
            stringId
        )
        else {
            when (stringId.lowercase(Locale.getDefault())) {

                "del" -> {
                    removeLastLetter()
                }
                "enter" -> {
                    makeGuess()
                }
                "no-id" -> {
                    Timber.i("----- $stringId")
                }
            }
        }

    }

    private fun makeGuess() {

        if (guessingWord.length == 5) {

            if (guessingTry < 6) {
                if (guessingWord.lowercase(Locale.getDefault()) == viewModel.word.value?.removeAccents()) {
                    Toast.makeText(requireContext(), R.string.right_word, Toast.LENGTH_SHORT).show()
                    paintLetters()
                } else {
                    Toast.makeText(requireContext(), R.string.wrong_word, Toast.LENGTH_SHORT).show()
                    paintLetters()
                    guessingWord = ""
                    guessingTry++
                    selectRow()

                }
            } else {
                paintLetters()
                Toast.makeText(
                    requireContext(),
                    getString(R.string.gameover, viewModel.word.value),
                    Toast.LENGTH_SHORT
                ).show()
            }


        } else {

            Toast.makeText(requireContext(), R.string.invalid_word, Toast.LENGTH_SHORT).show()

        }

    }

    private fun paintLetters() {
        for (indice in rowOfLetter.indices) {
            Timber.i("----- ${rowOfLetter[indice].text}")
            viewModel.word.value?.let {
                Timber.i("----- ${rowOfLetter[indice].text}")
                Timber.i("----- ${rowOfLetter[indice].text}")

                if (it.contains(
                        rowOfLetter[indice].text.toString().lowercase(Locale.getDefault())
                    ) && rowOfLetter[indice].text.toString()
                        .lowercase(Locale.getDefault()) != it[indice].toString()
                ) {
                    Timber.i("----- caiu no palavra na posição errada")
                    rowOfLetterFields[indice].setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.right_letter_wrong_place_background
                        )
                    )
                    paintKeyboard(
                        R.color.right_letter_wrong_place_background,
                        rowOfLetter[indice].text.toString()
                    )
                } else if (rowOfLetter[indice].text.toString()
                        .lowercase(Locale.getDefault()) == it[indice].toString()
                ) {
                    rowOfLetterFields[indice].setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.right_letter_right_place_background
                        )
                    )
                    paintKeyboard(
                        R.color.right_letter_right_place_background,
                        rowOfLetter[indice].text.toString()
                    )

                } else {
                    rowOfLetterFields[indice].setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.wrong_letter_background
                        )
                    )
                    paintKeyboard(
                        R.color.wrong_letter_background,
                        rowOfLetter[indice].text.toString()
                    )
                }
            }

        }
    }

    private fun paintKeyboard(color: Int, letter: String) {

        keyboardRow.forEach {
            if (it.getNamedId() == letter) {
                it.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        color
                    )
                )
            }
        }


    }

    private fun resetKeyboard() {
        rowOfLetterFields.forEach {
            it.setBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.empty_space_background)
            )
        }
    }

    private fun removeLastLetter() {
        Timber.i("----- $guessingWord")
        guessingWord = guessingWord.dropLast(1)
        Timber.i("----- $guessingWord")
        updateRow()
    }

    private fun updateGuessingWord(stringId: String) {
        if (guessingWord.length < 5) {
            guessingWord += stringId
            updateRow()
        }
    }
}