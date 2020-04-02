package com.cdd.smartsurvey

object GlobalValue {
    var uid = "0"
    var loginid = "0"
    var qestionerid = "0"
    var signature = ""
    var homeno = ""
    val EXTRA_COMMUNITY = "EXTRA_COMMUNITY"
    val EXTRA_FAMILY = "EXTRA_FAMILY"
    val EXTRA_FAMILY_INDEX = "EXTRA_FAMILY_INDEX"
    val EXTRA_SURVEY_GROUP = "EXTRA_SURVEY_GROUP"
    val EXTRA_SURVEY_METRIC = "EXTRA_SURVEY_METRIC"

    @JvmField
    var dbUrl = "http://27.254.121.113/SmartSurveyAPI_war_exploded/function/"
    var apiUrl = "http://app.hummingwing.com/cdd/api/"
    var apiToken = "Q09NTVVOSVRZIERFVkVMT1BNRU5UIERFUEFSVE1FTlQ="

    @JvmField
    var DetailMember1 = "สมาชิกในครัวเรือนในปีปัจจุบัน หมายถึง สมาชิกที่อยู่ประจำในครัวเรือนนี้ (คนที่ไม่ได้อยู่ประจำ แต่ไป ๆ มา ๆ ในรอบปีที่ผ่านมา ต้องมีเวลารวมกันไม่น้อยกว่า 6 เดือน)"

    @JvmField
    var DetailMember2 = "ครัวเรือน หมายถึง ครอบครัวที่อยู่ในบ้านเรือนเดียวกัน กินอยู่ ใช้จ่ายร่วมกัน ครัวเรือนหนึ่ง ๆ อาจมีหลายครอบครัวก็ได้"

    @JvmField
    var DetailMember3 = """
        คนที่มีอายุมากกว่า 2 ปีขึ้นไป ไม่ต้องใส่เศษเดือน แต่ถ้าอายุไม่ถึง 2 ปี ให้ระบุเดือนด้วย เช่น อายุ 1 ปี 11 เดือน หรือ 1 ปี 1 เดือน หรือ อายุ 11 เดือน หรือ อายุ 3 เดือน เป็นต้น
        และในกรณีที่อายุไม่ถึง 1 เดือน เช่น อายุ 10 วัน ให้ระบุอายุเป็น 1 เดือน

        """.trimIndent()

    @JvmField
    var DetailMember4 = "สถานะทางร่างกาย คือ ปกติ  ผู้พิการ หรือผู้ป่วยเรื้อรัง"

    @JvmField
    var DetailMember5 = "ข้อมูลคนต่างด้าว ในกรณีครัวเรือนคนไทยที่มีคนต่างด้าวร่วมอาศัยอยู่ด้วย ให้กรอกข้อมูลสมาชิกในครัวเรือนที่เป็นคนต่างด้าวเฉพาะในหน้าตารางข้อมูลสมาชิกนี้เท่านั้น แต่ไม่ต้องสำรวจข้อมูลคุณภาพชีวิตของคนต่างด้าว ในหน้า 6-29"
}