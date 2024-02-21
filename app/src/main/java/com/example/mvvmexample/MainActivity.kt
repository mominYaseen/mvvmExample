package com.example.mvvmexample

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.security.KeyStore.TrustedCertificateEntry
import java.time.ZoneId
import java.util.concurrent.CopyOnWriteArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var fab : FloatingActionButton
    private lateinit var dialogName : EditText
    private lateinit var dialogNum :EditText
    private lateinit var dialogImg :ImageView
    private lateinit var dialogImageBtn :Button
    private lateinit var dialogAddContactBtn : Button
    private lateinit var rv :RecyclerView
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var dialog: Dialog

    private lateinit var repo: Repo
    private lateinit var viewModel: ContactViewModel
    private lateinit var viewModelFactory: ContactViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repo = Repo()
        viewModelFactory = ContactViewModelFactory(repo)
        viewModel = ViewModelProvider(this,viewModelFactory).get(ContactViewModel::class.java)

        rv=findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)

        fab = findViewById(R.id.fab)

        viewModel.listOfContactLiveData.observe(this){
            contactAdapter=ContactAdapter(it)
            rv.adapter = contactAdapter
        }

        fab.setOnClickListener{
            Toast.makeText(this, "fab pressed", Toast.LENGTH_SHORT).show()
            showDialog()
        }


    }
    fun showDialog(){
         dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box)

        dialogName = dialog.findViewById(R.id.dialog_name)
        dialogNum = dialog.findViewById(R.id.dialog_num)
        dialogImg = dialog.findViewById(R.id.dialog_image)
        dialogImageBtn = dialog.findViewById(R.id.dialog_image_button)
        dialogAddContactBtn = dialog.findViewById(R.id.dialog_add_contact)

        dialogImageBtn.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,101)
        }

        dialog.show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==101 && resultCode == RESULT_OK){
            dialogImg.setImageURI(data?.data)
            dialogImg.visibility = View.VISIBLE

            dialogAddContactBtn.setOnClickListener{
                val headingName = dialogName.text.toString()
                val subHeading = dialogNum.text.toString()
                val imgPreview = data?.data

                val contact = Contact(
                    headingText = headingName,
                    subText = subHeading,
                    image = imgPreview!!
                )

                viewModel.addContact(contact)

                dialog.dismiss()
            }
        }
    }
}