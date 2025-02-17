import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.bookmyslot_tag.database.UserModel

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    init {
        val dbPath = context.getDatabasePath(DATABASE_NAME).absolutePath
        Log.d("DatabaseHelper", "Database Path: $dbPath")
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_USERNAME TEXT PRIMARY KEY, 
                $COLUMN_EMAIL TEXT UNIQUE, 
                $COLUMN_PASSWORD TEXT
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
        Log.d("DatabaseHelper", "User table created")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun insertUser(user: UserModel): Boolean {
        val db = this.writableDatabase

        val checkQuery = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_USERNAME = ? OR $COLUMN_EMAIL = ?"
        val cursor = db.rawQuery(checkQuery, arrayOf(user.username, user.email))

        if (cursor.count > 0) {
            Log.d("DatabaseHelper", "User already exists: ${user.username}")
            cursor.close()
            db.close()
            return false
        }

        val values = ContentValues().apply {
            put(COLUMN_USERNAME, user.username)
            put(COLUMN_EMAIL, user.email)
            put(COLUMN_PASSWORD, user.password) // TODO: Hash password before storing
        }

        val result = db.insert(TABLE_USERS, null, values)
        cursor.close()
        db.close()

        if (result == -1L) {
            Log.d("DatabaseHelper", "User registration failed")
            return false
        }

        Log.d("DatabaseHelper", "User registered: ${user.username}")
        return true
    }

    fun checkUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(username, password))

        Log.d("DatabaseHelper", "Checking user: $username with password: $password")

        val exists = cursor.count > 0
        cursor.close()
        db.close()

        if (exists) {
            Log.d("DatabaseHelper", "Login successful for $username")
        } else {
            Log.d("DatabaseHelper", "Invalid credentials for $username")
        }

        return exists
    }

    fun getAllUsers(): List<UserModel> {
        val db = this.readableDatabase
        val userList = mutableListOf<UserModel>()

        val cursor = db.rawQuery("SELECT * FROM $TABLE_USERS", null)
        while (cursor.moveToNext()) {
            val username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME))
            val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
            val password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
            Log.d("DatabaseHelper", "Retrieved user - $username, $email, $password")

            userList.add(UserModel(username, email, password))
        }
        cursor.close()
        db.close()

        return userList
    }

    companion object {
        private const val DATABASE_NAME = "bookmyslot.db"
        private const val DATABASE_VERSION = 2
        private const val TABLE_USERS = "users"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PASSWORD = "password"
    }
}
