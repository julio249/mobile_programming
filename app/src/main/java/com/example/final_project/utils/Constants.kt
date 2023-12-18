package com.example.final_project.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap

object Constants {

    const val FCM_TOKEN_UPDATED = "fcmTokenUpdated"
    const val FCM_TOKEN = "fcmToken"

    // Firebase Constants
    // This  is used for the collection name for USERS.
    const val USERS: String = "users"
    const val TASKS: String = "tasks"
    const val BOARDS:String = "boards"
    const val IMAGE: String = "image"
    const val NAME: String = "name"
    const val MOBILE: String = "mobile"
    const val ASSIGNED_TO:String ="assignedTo"
    const val REQUEST_IMAGE_GALLERY = 2
    const val PERMISSION_REQUEST_CODE = 1
    const val DOCUMENT_ID: String = "documentId"
    const val TASK_LIST: String = "taskList"
    const val TASK_CREATED_BY = "createdBy"
    const val BOARD_DETAIL: String = "board_detail"
    const val ID: String = "id"
    const val EMAIL: String = "email"
    const val TASK_LIST_ITEM_POSITION: String = "task_list_item_position"
    const val CARD_LIST_ITEM_POSITION: String =" card_list_item_position"
    const val BOARD_MEMBERS_LIST: String = "board_members_list"
    const val SELECT: String = "select"
    const val UN_SELECT: String= "unSelect"
    const val PROJECTMANAG_PREFERENCES = "ProjectmanagePrefs"

    const val FCM_BASE_URL:String = "https://fcm.googleapis.com/fcm/send"
    const val FCM_AUTHORIZATION:String = "authorization"
    const val FCM_KEY:String = "key"
    const val FCM_SERVER_KEY:String = "BNvw2was4_S_Cbs1bWEvoT30vWP4DuDDNigQbvHQg0u--VHx884HnXYgdhgzu-HJtRgI7dHnNhw3yclNb4oLQCs"
    const val FCM_KEY_TITLE:String = "title"
    const val FCM_KEY_MESSAGE:String = "message"
    const val FCM_KEY_DATA:String = "data"
    const val FCM_KEY_TO:String = "to"


    fun choosePhotoFromGallery(activity: Activity)
    {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
    }

    fun getFileExtension(activity: Activity,uri: Uri?): String?{
        return MimeTypeMap
            .getSingleton()
            .getMimeTypeFromExtension(activity.contentResolver.getType(uri!!))
    }



}