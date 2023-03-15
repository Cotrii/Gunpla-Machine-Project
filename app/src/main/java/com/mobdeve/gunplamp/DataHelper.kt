package com.mobdeve.gunplamp

// your package name

// Do not modify this file except for the package name

class DataHelper {
    companion object {
        fun initializeData(): ArrayList<Post> {
            val users = arrayOf(
                User("nugundam237", "pass1", "Amuro", "Ray", R.drawable.emblem),
                User("thehawk", "pass2", "Borat", "Sagdiyev", R.drawable.borat),
                User("mechagaikotsu", "pass3", "Mecha", "Gaikotsu", R.drawable.mechagaikotsu))

            val stores = arrayOf(
                Store("Toys R Us Uptown Mall", "Taguig"),
                Store("SM Masinag", "Rizal"),
                Store("Toytown Glorietta", "Makati City"))

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
                    "I found Pharact, very nice!",
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