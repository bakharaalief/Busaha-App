package com.busaha.busahaapp.util

import com.busaha.busahaapp.domain.model.Trend

object DataDummy {
    fun trendDummy(): List<Trend> {
        val result = ArrayList<Trend>()

        result.add(
            Trend(
                id = 0,
                title = "Katering Sehat",
                desc = "akhir-akhir ini gaya hidup sehat menjadi trend di kalangan masyarakat. " +
                        "Oleh karena itu, Anda bisa memanfaatkannya dengan membuka bisnis katering sehat. " +
                        "Rekomendasi usaha rumahan satu ini nggak ribet, kok. Dengan hanya bermodalkan dapur dan bahan makanan, sudah dapat menghasilkan banyak keuntungan."
            )
        )

        result.add(
            Trend(
                id = 1,
                title = "Kopi Susu Kekinian",
                desc = "Salah satu rekomendasi usaha rumahan yang lagi trend adalah bisnis warung kopi. " +
                        "Tenang, Anda tidak harus memiliki sebuah kedai ataupun food truck. " +
                        "Tinggal bermitra dengan Grab atau Gojek untuk layanan antarnya, Anda sudah bisa memulai usaha kopi susu kekinian di rumah."
            )
        )

        result.add(
            Trend(
                id = 2,
                title = "Laundry",
                desc = "Jika Anda mencari ide dan peluang usaha yang menjanjikan, cobalah bisnis laundry. " +
                        "Yup, apalagi jika Anda membukanya di area perkantoran, kampus, atau komplek perumahan baru. " +
                        "Pasalnya, kawasan tersebut umumnya dihuni oleh karyawan, mahasiswa, dan pasangan muda yang super sibuk."
            )
        )

        result.add(
            Trend(
                id = 3,
                title = "Guru les privat",
                desc = "Rekomendasi usaha rumahan yang lagi trend selanjutnya ialah guru les privat. " +
                        "Anda bisa memanggil para murid ke rumah atau sebaliknya, Anda yang datang menghampiri mereka. " +
                        "Pilih saja satu mata pelajaran atau beberapa yang Anda kuasai. Semakin banyak siswa yang Anda miliki, penghasilan pun pasti akan mengikuti."
            )
        )

        result.add(
            Trend(
                id = 4,
                title = "Translator",
                desc = "Anda juga dapat menyediakan jasa translator dengan skill bahasa asing yang Anda miliki. " +
                        "Rekomendasi usaha rumahan yang lagi trend ini tidak membutuhkan modal besar. " +
                        "Cukup dengan laptop dan jaringan internet, Anda sudah bisa memiliki pelanggan mulai dari mahasiswa hingga pebisnis. " +
                        "Usaha yang menjanjikan, bukan?"
            )
        )

        return result
    }
}