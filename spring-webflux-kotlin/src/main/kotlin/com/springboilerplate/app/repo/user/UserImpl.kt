package com.springboilerplate.app.repo.user

import com.springboilerplate.app.models.Provider
import com.springboilerplate.app.models.User
import com.springboilerplate.app.utils.DateTime
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.davidmoten.rx.jdbc.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
class UserImpl : UserRepo {
    @Autowired
    lateinit var db: Database

    internal fun toUser(rs: ResultSet): User {
        val user = User(
                id = rs.getLong("id"),
                name = rs.getString("name"),
                email = rs.getString("email"),
                provider = Provider.valueOf(rs.getString("provider")),
                picture = rs.getString("picture")
        )
        user.createdAt = DateTime.convertStrTime(
                rs.getString("created_at")
        )!!
        user.updatedAt = DateTime.convertStrTime(
                rs.getString("updated_at")
        )!!
        user.deletedAt = DateTime.convertStrTime(
                rs.getString("deleted_at")
        )
        return user
    }

        override suspend fun fetchUser(id: Long): User? = db
                .select("select * from users where id = $id")
                .get { rs -> toUser(rs) }
                .awaitFirstOrNull()
}