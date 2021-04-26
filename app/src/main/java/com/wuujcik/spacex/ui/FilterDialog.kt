package com.wuujcik.spacex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wuujcik.spacex.databinding.DialogFilterBinding
import com.wuujcik.spacex.utils.resizeDialogWindowToMatchScreen


class FilterDialog : DialogFragment() {

    private lateinit var binding: DialogFilterBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return DialogFilterBinding.inflate(inflater, container, false)
                .also {
                    binding = it
                }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            datePickerFrom.updateDate(2000, 11, 1) // TODO


            confirmButton.setOnClickListener { dialog?.dismiss() } //TODO
            cancelButton.setOnClickListener { dialog?.dismiss() }
        }
    }

    override fun onResume() {
        super.onResume()
        resizeDialogWindowToMatchScreen()
    }

    companion object {
        const val TAG = "DialogFragment"
    }
}