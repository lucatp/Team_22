package com.Table.Server.DatabaseObjects
import org.jetbrains.exposed.sql.*
import org.mindrot.jbcrypt.BCrypt
import java.util.regex.Pattern

data class User(val id: Int?=null, val username: String, val email:String, val password:String, val bio_id:Int?=null)
data class UserCredentials(val username: String, val password:String, val salt:String?)
object Users: Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val username: Column<String> = varchar("username", 255)
    val email: Column<String> = varchar("email", 255)
    val passwordHash: Column<String> = varchar("passwordHash", 255)
    val salt: Column<String> = varchar("salt", 255)
    val bioId = (integer("bio_id") references Bios.bio_id).nullable()
    override val primaryKey = PrimaryKey(id, name = "User_ID")

    init {
        index(true, username)
        index(true, email)
    }

    fun toUser(row: ResultRow): User = User(
        id = row[id],
        username = row[username],
        email = row[email],
        password = "",
        bio_id = row[bioId]
    )
    fun toUserCredentials(row: ResultRow): UserCredentials = UserCredentials(
        username = row[username],
        password = row[passwordHash],
        salt = row[salt]
    )
    //start
    //Taken from https://stackoverflow.com/questions/1819142/how-should-i-validate-an-e-mail-address
    val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    fun emailValidator (email:String) = EMAIL_ADDRESS_PATTERN.matcher(email).matches()

    //first input from request, second is result from database
    fun credentialsEquals(cred1: UserCredentials, cred2: UserCredentials): Boolean{
        var salt:String? = cred2.salt
        var hashedPassword: String = cred2.password
        var plainPassword: String = cred1.password
        var calculatedHash = BCrypt.hashpw(plainPassword, salt)
        if(calculatedHash.equals(hashedPassword))
        {
            return true
        }
        return false
    }

    //end

}
