package com.example.data

import androidx.compose.runtime.mutableStateListOf
import com.example.R

data class Product(
    val id: String,
    val name: String,
    val priceStr: String,
    val shortDesc: String,
    val fullDesc: String,
    val imageResId: Int,
    val categoryId: String,
    val materials: String,
    val usage: String
)

data class Category(
    val id: String,
    val name: String
)

object SampleData {
    val categories = listOf(
        Category("stone", "سنگ مصنوعی"),
        Category("candle", "شمع دست‌ساز"),
        Category("sets", "ست هدیه لوکس")
    )

    val products = mutableStateListOf(
        Product(
            id = "p1",
            name = "سنگ مصنوعی دکوراتیو",
            priceStr = "استعلام قیمت",
            shortDesc = "قطعات دکوری لوکس سنگ مصنوعی با طراحی مدرن و مینیمال.",
            fullDesc = "این محصولات با استفاده از بهترین پودر سنگ مصنوعی و رزین‌های مقاوم ساخته شده‌اند. طراحی خاص آن‌ها جلوه‌ای مدرن به دکوراسیون شما می‌بخشد.",
            imageResId = R.drawable.img_placeholder_stone_1784754783098,
            categoryId = "stone",
            materials = "پودر سنگ مصنوعی باکیفیت، رزین محافظ",
            usage = "مناسب برای تزئین میز، زیرشمعی، یا هدیه. با دستمال مرطوب به آرامی تمیز شود."
        ),
        Product(
            id = "p2",
            name = "شمع دست‌ساز معطر",
            priceStr = "استعلام قیمت",
            shortDesc = "شمع‌های لوکس و معطر ساخته شده از موم گیاهی سویا.",
            fullDesc = "شمع‌های دست‌ساز لونا آرت با رایحه‌های آرام‌بخش و ظاهری زیبا، فضایی گرم و دلنشین در خانه شما ایجاد می‌کنند. بدون دود و کاملاً گیاهی.",
            imageResId = R.drawable.img_placeholder_candle_1784754799950,
            categoryId = "candle",
            materials = "موم سویا (۱۰۰٪ گیاهی)، فیتیله نخی، اسانس‌های روغنی",
            usage = "روی سطح صاف و نسوز قرار دهید. قبل از روشن کردن فیتیله را کوتاه کنید."
        ),
        Product(
            id = "p3",
            name = "ست هدیه لوکس",
            priceStr = "استعلام قیمت",
            shortDesc = "پکیج هدیه شامل شمع‌های دست‌ساز و استند سنگ مصنوعی.",
            fullDesc = "یک هدیه بی‌نظیر برای عزیزان شما. این ست ترکیبی از زیبایی سنگ مصنوعی و آرامش شمع‌های معطر است که در بسته‌بندی لوکس ارائه می‌شود.",
            imageResId = R.drawable.img_placeholder_set_1784754814896,
            categoryId = "sets",
            materials = "سنگ مصنوعی، موم سویا گیاهی، جعبه هدیه اختصاصی",
            usage = "آماده برای هدیه دادن. دور از نور مستقیم خورشید نگهداری شود."
        ),
        Product(
            id = "p4",
            name = "گلدان مینیاتوری سنگ مصنوعی",
            priceStr = "استعلام قیمت",
            shortDesc = "گلدان‌های ظریف و زیبای سنگ مصنوعی برای دکوراسیون.",
            fullDesc = "این گلدان‌های مینیاتوری با طراحی خاص و رنگ‌های متنوع، انتخابی عالی برای میز کار یا دکوراسیون منزل شما هستند.",
            imageResId = R.drawable.img_placeholder_stone2_1784755576693,
            categoryId = "stone",
            materials = "پودر سنگ مصنوعی، رنگدانه طبیعی، رزین",
            usage = "مناسب برای گل‌های خشک و دکور. فقط با دستمال خشک تمیز شود."
        ),
        Product(
            id = "p5",
            name = "شمع معطر شیشه‌ای",
            priceStr = "استعلام قیمت",
            shortDesc = "شمع معطر در ظرف شیشه‌ای لوکس با ماندگاری بالا.",
            fullDesc = "شمع‌های شیشه‌ای لونا آرت با استفاده از موم سویا و اسانس‌های باکیفیت ساخته می‌شوند. این شمع‌ها مدت زمان سوخت طولانی دارند و رایحه‌ای ماندگار در فضا پخش می‌کنند.",
            imageResId = R.drawable.img_placeholder_candle2_1784755594016,
            categoryId = "candle",
            materials = "ظرف شیشه‌ای، موم سویا، اسانس عطری",
            usage = "پس از هر بار استفاده درب ظرف را ببندید تا عطر آن حفظ شود."
        ),
        Product(
            id = "p6",
            name = "پکیج هدیه آرامش",
            priceStr = "استعلام قیمت",
            shortDesc = "یک ست کامل از محصولات لونا آرت برای ایجاد حس آرامش.",
            fullDesc = "این پکیج شامل ترکیبی از بهترین محصولات سنگ مصنوعی و شمع‌های معطر ماست. یک هدیه ارزشمند و لوکس برای مناسبت‌های خاص.",
            imageResId = R.drawable.img_placeholder_set2_1784755610145,
            categoryId = "sets",
            materials = "محصولات متنوع سنگی و شمع، جعبه کادویی درجه یک",
            usage = "آماده برای هدیه دادن."
        )
    )
}
