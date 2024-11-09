package com.thales.productmanager.ui.createproduct

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import com.thales.productmanager.R
import com.thales.productmanager.data.local.model.Product
import com.thales.productmanager.databinding.FragmentProductCreationBinding
import com.thales.productmanager.ui.core.UiState
import com.thales.productmanager.ui.createproduct.state.ProductCreationUiState
import com.thales.productmanager.ui.createproduct.viewmodel.ProductCreationViewModel
import com.thales.productmanager.util.showLongToast
import com.thales.productmanager.util.trimmedText
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductCreationFragment : Fragment() {

    private lateinit var binding: FragmentProductCreationBinding

    private val viewModel: ProductCreationViewModel by viewModels()

    private var progressDialog: ProgressDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProductCreationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setViewModelObservers()
    }

    private fun showProgress() {
        if (progressDialog?.isShowing == true) {
            return
        }
        ProgressDialog(requireContext()).let {
            it.show()
            progressDialog = it
        }

    }

    private fun dismissProgress() {
        progressDialog?.let {
            if (!it.isShowing) {
                return
            }
            it.dismiss()
            progressDialog = null
        }
    }

    private fun setViewModelObservers() {
        viewModel.productCreationUiState.observe(viewLifecycleOwner) { handleProductCreationUiState(it) }
    }

    private fun handleProductCreationUiState(productCreationUiState: ProductCreationUiState) {
        dismissProgress()
        productCreationUiState.errorDetail?.let {
            requireContext().showLongToast(getString(R.string.msg_general_failure))
        } ?: run {
            requireContext().showLongToast(getString(R.string.msg_product_creation_success))
            clearFields()
        }
    }

    private fun clearFields() {
        with(binding) {
            nameEdtxt.setText("")
            typeEdtxt.setText("")
            descriptionEdtxt.setText("")
            priceEdtxt.setText("")
        }
    }

    private fun initView() = with(binding) {
        createBtn.setOnClickListener {
            handleCreateBtnClick()
        }
        return@with
    }

    private fun handleCreateBtnClick() {
        val productDetailValid = validateProductDetail()
        if (!productDetailValid) {
            requireContext().showLongToast(getString(R.string.error_msg_product_input_invalid))
            return
        }
        val product = Product(
            name = binding.nameEdtxt.trimmedText(),
            type = binding.typeEdtxt.trimmedText(),
            description = binding.descriptionEdtxt.trimmedText(),
            imageId = null,
            price = binding.priceEdtxt.trimmedText().toDouble()
        )
        viewModel.createProduct(product)
        showProgress()
    }

    private fun validateProductDetail(): Boolean = with(binding) {
        if (nameEdtxt.trimmedText().isEmpty()) {
            return false
        }
        if (nameEdtxt.trimmedText().isEmpty()) {
            return false
        }
        if (nameEdtxt.trimmedText().isEmpty()) {
            return false
        }
        if (nameEdtxt.trimmedText().isEmpty()) {
            return false
        }
        return true
    }
}