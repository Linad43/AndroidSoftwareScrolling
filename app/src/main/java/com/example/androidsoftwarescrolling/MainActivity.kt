package com.example.androidsoftwarescrolling

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidsoftwarescrolling.ui.theme.Purple40

val arrayFirstName = arrayListOf(
    "Даниил", "Ефим", "Кирилл", "Максим", "Егор", "Борис", "Тимофей", "Влад"
    , "Игорь", "Аркадий", "Трофим", "Виктор", "Валерий"
)
val arraySecondName = arrayListOf(
    "Цивилев", "Баданин", "Шнайдер", "Фролов", "Рогачев", "Фомин", "Лавров", "Седых"
    , "Ткаченко", "Иванов", "Долгих", "Базовкин", "Кабанов", "цивилев"
)

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            fillingPersons(80, false)
            var group = persons.groupBy { it.role }
            GroupindPersons(group)
        }
    }
}

enum class Role {
    ENGINEER, DEVELOPER, DOCTOR, TEACHER
}

class Person(
    val firstName: String,
    val secondName: String,
    val role: Role,
    val salary: Int,
    val image: Int,
)

val persons = arrayListOf<Person>()
fun fillingPersons(count: Int, equalDistribution: Boolean) {
    var i = 0
    while (i < count) {
        persons.add(
            Person(
                arrayFirstName.random(),
                arraySecondName.random(),
                if (equalDistribution) {
                    Role.entries.toTypedArray()[i % Role.entries.toTypedArray().size]
                } else {
                    Role.entries.toTypedArray().random()
                },
                (50..120).random() * 1000,
                R.drawable.ic_launcher_foreground
            )
        )
        i++
    }
}

@ExperimentalFoundationApi
@Composable
fun GroupindPersons(group: Map<Role, List<Person>>) {
    LazyColumn(
        contentPadding = PaddingValues(6.dp)
    ) {
        group
            .toSortedMap(compareBy { it })
            .forEach { (role, persons) ->
            stickyHeader {
                Text(
                    text = role.toString(),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .background(Purple40)
                        .padding(6.dp)
                        .fillParentMaxWidth()
                )
            }
            items(persons.sortedWith(compareBy({ it.secondName }, { it.firstName }))) {
                Text(
                    text = "${it.secondName} ${it.firstName}",
                    modifier = Modifier
                        .padding(6.dp),
                    fontSize = 32.sp
                )
            }
        }
    }
}