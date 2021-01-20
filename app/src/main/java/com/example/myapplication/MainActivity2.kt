package com.example.myapplication

import android.content.Context
import android.content.DialogInterface
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MesComptesActivity : AppCompatActivity() {
    var cur: Cursor? = null
    var db: SQLiteDatabase? = null
    var layNaviguer: LinearLayout? = null
    var layRecherche: LinearLayout? = null
    var _txtSiteWeb: EditText? = null
    var _txtApplication: EditText? = null
    var _txtCompte: EditText? = null
    var _txtPassword: EditText? = null
    var _txtRechercheSiteWeb: EditText? = null
    var _btnRecherche: ImageButton? = null
    var _btnFirst: Button? = null
    var _btnPrevious: Button? = null
    var _btnNext: Button? = null
    var _btnLast: Button? = null
    var _btnAdd: Button? = null
    var _btnUpdate: Button? = null
    var _btnDelete: Button? = null
    var _btnCancel: Button? = null
    var _btnSave: Button? = null
    var op = 0
    var x: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mes_comptes)
        layNaviguer = findViewById<View>(R.id.layNaviguer) as LinearLayout
        layRecherche = findViewById<View>(R.id.layRecherche) as LinearLayout
        _txtRechercheSiteWeb =
            findViewById<View>(R.id.txtRechercheSiteWeb) as EditText
        _txtSiteWeb = findViewById<View>(R.id.txtSiteWeb) as EditText
        _txtApplication = findViewById<View>(R.id.txtApplication) as EditText
        _txtCompte = findViewById<View>(R.id.txtCompte) as EditText
        _txtPassword = findViewById<View>(R.id.txtPassword) as EditText
        _btnAdd = findViewById<View>(R.id.btnAdd) as Button
        _btnUpdate = findViewById<View>(R.id.btnUpdate) as Button
        _btnDelete = findViewById<View>(R.id.btnDelete) as Button
        _btnFirst = findViewById<View>(R.id.btnFirst) as Button
        _btnPrevious = findViewById<View>(R.id.btnPrevious) as Button
        _btnNext = findViewById<View>(R.id.btnNext) as Button
        _btnLast = findViewById<View>(R.id.btnLast) as Button
        _btnCancel = findViewById<View>(R.id.btnCancel) as Button
        _btnSave = findViewById<View>(R.id.btnSave) as Button
        _btnRecherche = findViewById<View>(R.id.btnRecherche) as ImageButton

        // ouverture d'une connexion vers la base de données
        db = openOrCreateDatabase("AppAcc", Context.MODE_PRIVATE, null)
        // Création de la table comptes
        db.execSQL("CREATE TABLE IF NOT EXISTS COMPTES (id integer primary key autoincrement, siteweb VARCHAR, application VARCHAR, userid VARCHAR, pwd VARCHAR);")
        layNaviguer!!.visibility = View.INVISIBLE
        _btnSave!!.visibility = View.INVISIBLE
        _btnCancel!!.visibility = View.INVISIBLE
        _btnRecherche!!.setOnClickListener {
            cur = db.rawQuery(
                "select * from comptes where siteweb like ?",
                arrayOf("%" + _txtRechercheSiteWeb!!.text.toString() + "%")
            )
            try {
                cur.moveToFirst()
                _txtSiteWeb!!.setText(cur.getString(1))
                _txtApplication!!.setText(cur.getString(2))
                _txtCompte!!.setText(cur.getString(3))
                _txtPassword!!.setText(cur.getString(4))
                if (cur.getCount() == 1) {
                    layNaviguer!!.visibility = View.INVISIBLE
                } else {
                    layNaviguer!!.visibility = View.VISIBLE
                    _btnPrevious!!.isEnabled = false
                    _btnNext!!.isEnabled = true
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "aucun résultat.", Toast.LENGTH_SHORT)
                    .show()
                _txtSiteWeb!!.setText("")
                _txtApplication!!.setText("")
                _txtCompte!!.setText("")
                _txtPassword!!.setText("")
                layNaviguer!!.visibility = View.INVISIBLE
            }
        }
        _btnFirst!!.setOnClickListener {
            try {
                cur!!.moveToFirst()
                _txtSiteWeb!!.setText(cur!!.getString(1))
                _txtApplication!!.setText(cur!!.getString(2))
                _txtCompte!!.setText(cur!!.getString(3))
                _txtPassword!!.setText(cur!!.getString(4))
                _btnPrevious!!.isEnabled = false
                _btnNext!!.isEnabled = true
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    applicationContext,
                    "Aucun Compte N'est Existant.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        _btnLast!!.setOnClickListener {
            try {
                cur!!.moveToLast()
                _txtSiteWeb!!.setText(cur!!.getString(1))
                _txtApplication!!.setText(cur!!.getString(2))
                _txtCompte!!.setText(cur!!.getString(3))
                _txtPassword!!.setText(cur!!.getString(4))
                _btnPrevious!!.isEnabled = true
                _btnNext!!.isEnabled = false
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    applicationContext,
                    "Aucun Compte N'est Existant.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        _btnNext!!.setOnClickListener {
            try {
                cur!!.moveToNext()
                _txtSiteWeb!!.setText(cur!!.getString(1))
                _txtApplication!!.setText(cur!!.getString(2))
                _txtCompte!!.setText(cur!!.getString(3))
                _txtPassword!!.setText(cur!!.getString(4))
                _btnPrevious!!.isEnabled = true
                if (cur!!.isLast) {
                    _btnNext!!.isEnabled = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        _btnPrevious!!.setOnClickListener {
            try {
                cur!!.moveToPrevious()
                _txtSiteWeb!!.setText(cur!!.getString(1))
                _txtApplication!!.setText(cur!!.getString(2))
                _txtCompte!!.setText(cur!!.getString(3))
                _txtPassword!!.setText(cur!!.getString(4))
                _btnNext!!.isEnabled = true
                if (cur!!.isFirst) {
                    _btnPrevious!!.isEnabled = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        _btnAdd!!.setOnClickListener {
            op = 1
            _txtSiteWeb!!.setText("")
            _txtApplication!!.setText("")
            _txtCompte!!.setText("")
            _txtPassword!!.setText("")
            _btnSave!!.visibility = View.VISIBLE
            _btnCancel!!.visibility = View.VISIBLE
            _btnUpdate!!.visibility = View.INVISIBLE
            _btnDelete!!.visibility = View.INVISIBLE
            _btnAdd!!.isEnabled = false
            layNaviguer!!.visibility = View.INVISIBLE
            layRecherche!!.visibility = View.INVISIBLE
        }
        _btnUpdate!!.setOnClickListener { // tester si les champs ne sont pas vides
            try {
                x = cur!!.getString(0)
                op = 2
                _btnSave!!.visibility = View.VISIBLE
                _btnCancel!!.visibility = View.VISIBLE
                _btnDelete!!.visibility = View.INVISIBLE
                _btnUpdate!!.isEnabled = false
                _btnAdd!!.visibility = View.INVISIBLE
                layNaviguer!!.visibility = View.INVISIBLE
                layRecherche!!.visibility = View.INVISIBLE
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    applicationContext,
                    "Sélectionnez un compte puis appyuer sur le bouton de modification",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        _btnSave!!.setOnClickListener {
            if (op == 1) {
                // insertion
                db.execSQL(
                    "insert into comptes (siteweb,application,userid,pwd) values (?,?,?,?);",
                    arrayOf(
                        _txtSiteWeb!!.text.toString(),
                        _txtApplication!!.text.toString(),
                        _txtCompte!!.text.toString(),
                        _txtPassword!!.text.toString()
                    )
                )
            } else if (op == 2) {
                // Mise à jour
                db.execSQL(
                    "update comptes set siteweb=?, application=?, userid=?, pwd=? where id=?;",
                    arrayOf(
                        _txtSiteWeb!!.text.toString(),
                        _txtApplication!!.text.toString(),
                        _txtCompte!!.text.toString(),
                        _txtPassword!!.text.toString(),
                        x
                    )
                )
            }
            _btnSave!!.visibility = View.INVISIBLE
            _btnCancel!!.visibility = View.INVISIBLE
            _btnUpdate!!.visibility = View.VISIBLE
            _btnDelete!!.visibility = View.VISIBLE
            _btnAdd!!.visibility = View.VISIBLE
            _btnAdd!!.isEnabled = true
            _btnUpdate!!.isEnabled = true
            _btnRecherche!!.performClick()
            layRecherche!!.visibility = View.VISIBLE
        }
        _btnCancel!!.setOnClickListener {
            op = 0
            _btnSave!!.visibility = View.INVISIBLE
            _btnCancel!!.visibility = View.INVISIBLE
            _btnUpdate!!.visibility = View.VISIBLE
            _btnDelete!!.visibility = View.VISIBLE
            _btnAdd!!.visibility = View.VISIBLE
            _btnAdd!!.isEnabled = true
            _btnUpdate!!.isEnabled = true
            layRecherche!!.visibility = View.VISIBLE
        }
        _btnDelete!!.setOnClickListener {
            try {
                x = cur!!.getString(0)
                val dial = MesOptions()
                dial.show()
            } catch (e: Exception) {
                Toast.makeText(
                    applicationContext,
                    "Sélectionner un compte puis appyuer sur le bouton de suppresssion",
                    Toast.LENGTH_SHORT
                ).show()
                e.printStackTrace()
            }
        }
    }

    private fun MesOptions(): AlertDialog {
        return AlertDialog.Builder(this)
            .setTitle("Confirmation")
            .setMessage("Est ce que vous voulez supprimer ce compte?")
            .setIcon(R.drawable.validate)
            .setPositiveButton("Valider",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    db!!.execSQL(
                        "delete from comptes where id=?;",
                        arrayOf(cur!!.getString(0))
                    )
                    _txtSiteWeb!!.setText("")
                    _txtApplication!!.setText("")
                    _txtCompte!!.setText("")
                    _txtPassword!!.setText("")
                    layNaviguer!!.visibility = View.INVISIBLE
                    cur!!.close()
                    dialogInterface.dismiss()
                })
            .setNegativeButton("Annuler", new DialogInterface . OnClickListener () {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss()
                }
            })
            .create()
        return MiDia
    }
}

