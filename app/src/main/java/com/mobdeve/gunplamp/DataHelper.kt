package com.mobdeve.gunplamp

// your package name

// Do not modify this file except for the package name

class DataHelper {
    companion object {
        fun initializeData(): ArrayList<Post> {
            val usernames = arrayOf("Wonderer", "JustMe", "TravelingNomad", "WelcomeToMyLife")
            val userImages = intArrayOf(
                R.drawable.person1,
                R.drawable.person2,
                R.drawable.person3,
                R.drawable.person4
            )
            val users = arrayOf(User("Wonderer1", "pass1", "first1", "last1", R.drawable.person1),
                User("JustMe2", "pass2", "first2", "last2", R.drawable.person2),
                User("TravelingNomad3", "pass3", "first3", "last3", R.drawable.person3))

            val stores = arrayOf(Store("Store1", "ABC CITY FIRST"), Store("Store2", "DEF CITY SECOND"), Store("STORE3", "GHI CITY THIRD"))

            val data = ArrayList<Post>()
            data.add(
                Post(
                    users[0],
                    R.drawable.aerialsd.toString(),
                    "that's a tall boi",
                    stores[0],
                    "February 14, 2021",
                    false
                )
            )
            data.add(
                Post(
                    users[1],
                    R.drawable.pharact.toString(),
                    "just made lunch! ready to dig in #food #burgers #coke",
                    stores[1],
                    "December 25, 2020",
                    false
                )
            )
            data.add(
                Post(
                    users[2],
                    R.drawable.zowort.toString(),
                    "just made lunch! ready to dig in #food #burgers #coke",
                    stores[2],
                    "December 25, 2020",
                    true
                )
            )

            return data;
        }
    }
}