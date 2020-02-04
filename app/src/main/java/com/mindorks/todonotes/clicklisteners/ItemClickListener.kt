package com.mindorks.todonotes.clicklisteners

import com.mindorks.todonotes.db.Notes

interface ItemClickListener {
    fun onClick(notes: Notes)
    fun onUpdate(notes: Notes)
}