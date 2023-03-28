package com.mobdeve.gunplamp

class DataGenerator{
    companion object {
        fun generateData() : ArrayList<User> {
            val data = ArrayList<User>()

            data.add(
                User("123","nugundam237", "pass1", "Amuro", R.drawable.emblem)
            )

            data.add(
                User("124","thehawk", "pass2", "Borat",  R.drawable.borat)
            )

            data.add(
                User("125","mechagaikotsu", "pass3", "Mecha",  R.drawable.mechagaikotsu)
            )


            return data;

        }
    }
}