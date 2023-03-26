package com.mobdeve.gunplamp

// your package name

// Do not modify this file except for the package name

class DataHelper {
    companion object {
        fun initializeData(): ArrayList<Post> {
            val users = arrayOf(
                User("nugundam237", "pass1", "Amuro",  R.drawable.emblem),
                User("thehawk", "pass2", "Borat",  R.drawable.borat),
                User("mechagaikotsu", "pass3", "Mecha",  R.drawable.mechagaikotsu))

            val stores = arrayOf(
                Store("Toys R Us Uptown Mall", "Taguig"),
                Store("SM Masinag", "Rizal"),
                Store("Toytown Glorietta", "Makati City"))

            val data = ArrayList<Post>()
            data.add(
                Post(
                    users[0],
                    R.drawable.aerialsd.toString(),
                    "It can fly! It can dance! Aerial!",
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
                    "Available for P899! (Red tag)",
                    stores[2],
                    "December 25, 2020",
                    false
                )
            )

            return data;
        }
    }
}