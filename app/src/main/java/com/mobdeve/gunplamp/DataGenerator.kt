package com.mobdeve.gunplamp

class DataGenerator{
    companion object {
        fun generateData() : ArrayList<User> {
            val data = ArrayList<User>()

            data.add(
                User("nugundam237", "pass1", "Amuro", "Ray", R.drawable.emblem)
            )

            data.add(
                User("thehawk", "pass2", "Borat", "Sagdiyev", R.drawable.borat)
            )

            data.add(
                User("mechagaikotsu", "pass3", "Mecha", "Gaikotsu", R.drawable.mechagaikotsu)
            )


            return data;

        }
    }
}