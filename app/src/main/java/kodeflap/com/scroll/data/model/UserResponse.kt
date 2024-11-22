import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("users")
    var users: List<User> // List of users as per the API response
)

data class User(
    @SerializedName("id")
    var id: Int,

    @SerializedName("firstName")
    var firstName: String,

    @SerializedName("lastName")
    var lastName: String,

    @SerializedName("maidenName")
    var maidenName: String?,

    @SerializedName("age")
    var age: Int,

    @SerializedName("gender")
    var gender: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("phone")
    var phone: String,

    @SerializedName("username")
    var username: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("birthDate")
    var birthDate: String,

    @SerializedName("image")
    var image: String,

    @SerializedName("bloodGroup")
    var bloodGroup: String,

    @SerializedName("height")
    var height: Double,

    @SerializedName("weight")
    var weight: Double,

    @SerializedName("eyeColor")
    var eyeColor: String,

    @SerializedName("hair")
    var hair: Hair,

    @SerializedName("ip")
    var ip: String,

    @SerializedName("address")
    var address: Address,

    @SerializedName("macAddress")
    var macAddress: String,

    @SerializedName("university")
    var university: String,

    @SerializedName("bank")
    var bank: Bank,

    @SerializedName("company")
    var company: Company,

    @SerializedName("ein")
    var ein: String,

    @SerializedName("ssn")
    var ssn: String,

    @SerializedName("userAgent")
    var userAgent: String,

    @SerializedName("crypto")
    var crypto: Crypto,

    @SerializedName("role")
    var role: String
)

data class Hair(
    @SerializedName("color")
    var color: String,

    @SerializedName("type")
    var type: String
)

data class Address(
    @SerializedName("address")
    var address: String,

    @SerializedName("city")
    var city: String,

    @SerializedName("state")
    var state: String,

    @SerializedName("stateCode")
    var stateCode: String,

    @SerializedName("postalCode")
    var postalCode: String,

    @SerializedName("coordinates")
    var coordinates: Coordinates,

    @SerializedName("country")
    var country: String
)

data class Coordinates(
    @SerializedName("lat")
    var lat: Double,

    @SerializedName("lng")
    var lng: Double
)

data class Bank(
    @SerializedName("cardExpire")
    var cardExpire: String,

    @SerializedName("cardNumber")
    var cardNumber: String,

    @SerializedName("cardType")
    var cardType: String,

    @SerializedName("currency")
    var currency: String,

    @SerializedName("iban")
    var iban: String
)

data class Company(
    @SerializedName("department")
    var department: String,

    @SerializedName("name")
    var name: String,

    @SerializedName("title")
    var title: String,

    @SerializedName("address")
    var address: Address
)

data class Crypto(
    @SerializedName("coin")
    var coin: String,

    @SerializedName("wallet")
    var wallet: String,

    @SerializedName("network")
    var network: String
)
