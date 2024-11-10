package com.thales.productmanager.ui.createproduct

import android.app.ProgressDialog
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.thales.productmanager.R
import com.thales.productmanager.data.local.model.Product
import com.thales.productmanager.databinding.FragmentProductCreationBinding
import com.thales.productmanager.ui.createproduct.adapter.ProductImageListAdapter
import com.thales.productmanager.ui.createproduct.state.ProductCreationUiState
import com.thales.productmanager.ui.createproduct.viewmodel.ProductCreationViewModel
import com.thales.productmanager.util.DIR_PRODUCT_IMAGE
import com.thales.productmanager.util.MAX_PRODUCT_IMAGES
import com.thales.productmanager.util.showLongToast
import com.thales.productmanager.util.trimmedText
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ProductCreationFragment : Fragment() {

    companion object {
        private const val TAG = "ProductCreationFragment"
    }

    private lateinit var binding: FragmentProductCreationBinding

    private val viewModel: ProductCreationViewModel by viewModels()

    private var progressDialog: ProgressDialog? = null

    private val productImageListAdapter: ProductImageListAdapter = ProductImageListAdapter(listOf(), object : ProductImageListAdapter.Callback {
        override fun onRemoveImageBtnClick(position: Int) {
            handleRemoveImageBtnClick(position)
        }
    })

    private val productImagePicker = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(MAX_PRODUCT_IMAGES)) {
        handleProductImagePickerResult(it)
    }

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

    private fun handleProductImagePickerResult(imageUris: List<Uri>) {
        if (imageUris.isEmpty()) {
            return
        }
        Log.d(TAG, "selected imageUris = $imageUris")
        productImageListAdapter.updateImageUris(imageUris)
    }

    private fun handleRemoveImageBtnClick(position: Int) {
        productImageListAdapter.removeImage(position)
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
            productImageListAdapter.updateImageUris(listOf())
        }
    }

    private fun initView() = with(binding) {
        createBtn.setOnClickListener {
            handleCreateBtnClick()
        }
        addImageBtn.setOnClickListener {
            handleAddImageBtnClick()
        }
        productImageList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        productImageList.adapter = productImageListAdapter
        productImageList.addItemDecoration(object : ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                val spaceSize = resources.getDimensionPixelSize(R.dimen.product_image_spacing)
                with(outRect) {
                    if (parent.getChildAdapterPosition(view) == 0) {
                        top = spaceSize
                    }
                    left = spaceSize
                    right = spaceSize
                    bottom = spaceSize
                }
            }
        })
        return@with
    }

    private fun handleAddImageBtnClick() {
        productImagePicker.launch(PickVisualMediaRequest.Builder().setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly).build())
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
            imageFileNames = null,
            price = binding.priceEdtxt.trimmedText().toDouble()
        )
        viewModel.createProduct(
            product, productImageListAdapter.getImageUris(), requireContext().contentResolver, File(requireContext().filesDir, DIR_PRODUCT_IMAGE)
        )
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