package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    var _txtLogin: EditText? = null
    var _txtPassword: EditText? = null
    var _btnConnection: Button? = null
    var db: SQLiteDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _txtLogin = findViewById<View>(R.id.txtLogin) as EditText
        _txtPassword = findViewById<View>(R.id.txtPassword) as EditText
        _btnConnection = findViewById<View>(R.id.btnConnection) as Button

        // Création de la base de données ou ouverture de connexion
        db = openOrCreateDatabase("AppAcc", Context.MODE_PRIVATE, null)
        // Création de la table "users"
        db.execSQL("CREATE TABLE IF NOT EXISTS USERS (login varchar primary key, password varchar);")
        // si la table "users" est vide alors ajouter l'utilisateur admin avec mot de passe "lfsi3"
        val s = db.compileStatement("select count(*) from users;")
        val c = s.simpleQueryForLong()
        if (c == 0L) {
            db.execSQL("insert into users (login, password) values (?,?)", arrayOf("Borhen&Asma", sha256("lfsi3")))
        }
        _btnConnection!!.setOnClickListener {
            val strLogin = _txtLogin!!.text.toString()
            val strPwd = _txtPassword!!.text.toString()
            // Créer un curseur pour récupérer le résultat de la requête select
            val cur = db.rawQuery("select password from users where login =?", arrayOf(strLogin))
            try {
                cur.moveToFirst()
                val p = cur.getString(0)
                if (p == sha256(strPwd)) {
                    Toast.makeText(applicationContext, "Bienvenue $strLogin", Toast.LENGTH_LONG).show()
                    val i = Intent(applicationContext, MesComptesActivity::class.java)
                    startActivity(i)
                }else{
                    _txtLogin!!.setText("")
                    _txtPassword!!.setText("")
                    Toast.makeText(applicationContext, "Echec de connexion", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                _txtLogin!!.setText("")
                _txtPassword!!.setText("")
                Toast.makeText(applicationContext, "Utilisateur Inexistant", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }
}