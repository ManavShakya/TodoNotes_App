package com.mindorks.todonotes.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mindorks.todonotes.NotesApp
import com.mindorks.todonotes.utils.AppConsant.FULL_NAME
import com.mindorks.todonotes.R
import com.mindorks.todonotes.adapter.NotesAdapter
import com.mindorks.todonotes.clicklisteners.ItemClickListener
import com.mindorks.todonotes.db.Notes
import com.mindorks.todonotes.utils.AppConsant.ADD_NOTES_CODE
import com.mindorks.todonotes.utils.AppConsant.DESCRIPTION
import com.mindorks.todonotes.utils.AppConsant.IMAGE_PATH
import com.mindorks.todonotes.utils.AppConsant.TITLE
import com.mindorks.todonotes.utils.StoreSession
import com.mindorks.todonotes.workmanager.MyWorker
import java.util.concurrent.TimeUnit

class MyNotesActivity : AppCompatActivity(),ItemClickListener {

    private var fullName: String? = null
    private lateinit var buttonAddNotes: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private var noteslist = ArrayList<Notes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)

        setupSharedPreferences()
        bindViews()
        getIntentData()
        getDataFromDatabase()
        clickListeners()
        setupRecyclerView()
        setupWorkManager()
        supportActionBar?.title = fullName
    }

    private fun setupWorkManager() {
        val constraint = Constraints.Builder()
            .build()
        val request = PeriodicWorkRequest.Builder(MyWorker::class.java,1,TimeUnit.MINUTES)
            .setConstraints(constraint)
            .build()
        WorkManager.getInstance().enqueue(request)
    }

    private fun clickListeners() {

        buttonAddNotes.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                //setupDialog()
                val intent = Intent(this@MyNotesActivity,AddNotesActivity::class.java)
                startActivityForResult(intent, ADD_NOTES_CODE)
            }

        })
    }

    private fun getDataFromDatabase() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        noteslist.addAll(notesDao.getAll())
    }

    private fun setupSharedPreferences() {
        StoreSession.init(this)
    }

    private fun getIntentData() {
        val intent = intent
        fullName = intent.getStringExtra(FULL_NAME)
        if(TextUtils.isEmpty(fullName)){
            fullName = StoreSession.readString(FULL_NAME)
        }
        Log.d("MyNotesActivity",fullName+" ")
    }

    private fun bindViews() {
        buttonAddNotes = findViewById(R.id.fab)
        recyclerView = findViewById(R.id.recyclerViewNotes)
    }

    private fun setupDialogBox(){
        val view: View = LayoutInflater.from(this).inflate(R.layout.add_notes_dialog_layout,null)
        val editTextTitle: EditText = view.findViewById(R.id.editTextTitle)
        val editTextDescription: EditText = view.findViewById(R.id.editTextDescription)
        val buttonSubmit: Button = view.findViewById(R.id.buttonSubmit)
        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(false)
            .create()
        dialog.show()
        buttonSubmit.setOnClickListener {
            var title = editTextTitle.text.toString()
            var description = editTextDescription.text.toString()
            if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)) {
                val notes = Notes(title = title,description = description)
                noteslist.add(notes)
                addNotesToDb(notes)
            }

            else
                Toast.makeText(this,"Title/Description should not be empty",Toast.LENGTH_SHORT).show()

            dialog.hide()
        }
    }

    private fun addNotesToDb(notes: Notes) {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.insert(notes)
    }

    private fun setupRecyclerView() {
        val notesAdapter = NotesAdapter(noteslist,this)
        val linearLayoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = notesAdapter
    }

    override fun onClick(notes: Notes) {
        val intent: Intent = Intent(this,DetailActivity::class.java)
        intent.putExtra(TITLE,notes.title)
        intent.putExtra(DESCRIPTION,notes.description)
        startActivity(intent)
    }

    override fun onUpdate(notes: Notes) {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.updateNotes(notes)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ADD_NOTES_CODE){
            val title = data!!.getStringExtra(TITLE).toString()
            val description = data!!.getStringExtra(DESCRIPTION).toString()
            val imagepath = data!!.getStringExtra(IMAGE_PATH).toString()


            val notes = Notes(title = title,description = description,imagePath = imagepath,isTaskCompleted = false)
            addNotesToDb(notes)
            noteslist.add(notes)
            recyclerView.adapter?.notifyItemChanged(noteslist.size-1)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.blog){
            val intent = Intent(this@MyNotesActivity,BlogActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
