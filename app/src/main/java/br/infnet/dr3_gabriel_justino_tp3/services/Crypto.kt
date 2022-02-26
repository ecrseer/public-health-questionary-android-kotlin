package br.infnet.dr3_gabriel_justino_tp3.services

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.room.TypeConverter
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class Criptografador{
    val ks: KeyStore =
        KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

    fun getSecretKey(): SecretKey? {
        var chave: SecretKey? = null
        if(ks.containsAlias("chaveCripto")) {
            val entrada = ks.getEntry("chaveCripto", null) as?
                    KeyStore.SecretKeyEntry
            chave = entrada?.secretKey
        } else {
            val builder = KeyGenParameterSpec.Builder("chaveCripto",
                KeyProperties.PURPOSE_ENCRYPT or
                        KeyProperties.PURPOSE_DECRYPT)
            val keySpec = builder.setKeySize(256)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(
                    KeyProperties.ENCRYPTION_PADDING_PKCS7).build()
            val kg = KeyGenerator.getInstance("AES", "AndroidKeyStore")
            kg.init(keySpec)
            chave = kg.generateKey()
        }
        return chave
    }

    fun cipher(original: String): ByteArray {
        var chave = getSecretKey()
        return cipher(original,chave)
    }

    fun cipher(original: String, chave: SecretKey?): ByteArray {
        if (chave != null) {
            Cipher.getInstance("AES/CBC/PKCS7Padding").run {
                init(Cipher.ENCRYPT_MODE,chave)
                var valorCripto = doFinal(original.toByteArray())
                var ivCripto = ByteArray(16)
                iv.copyInto(ivCripto,0,0,16)
                return ivCripto + valorCripto
            }
        } else return byteArrayOf()
    }
    fun decipher(cripto: ByteArray): String{
        var chave = getSecretKey()
        return decipher(cripto,chave)
    }

    fun decipher(cripto: ByteArray, chave: SecretKey?): String{
        if (chave != null) {
            Cipher.getInstance("AES/CBC/PKCS7Padding").run {
                var ivCripto = ByteArray(16)
                var valorCripto = ByteArray(cripto.size-16)
                cripto.copyInto(ivCripto,0,0,16)
                cripto.copyInto(valorCripto,0,16,cripto.size)
                val ivParams = IvParameterSpec(ivCripto)
                init(Cipher.DECRYPT_MODE,chave,ivParams)
                return String(doFinal(valorCripto))
            }
        } else return ""
    }
}

class CriptoString {

    companion object{
        @JvmStatic
        val criptoGrafador = Criptografador()
    }
    private var cripto: ByteArray? = null

    // Valor em Base 64 para o banco
    fun getCriptoBase64(): String?{
        return Base64.encodeToString(cripto,Base64.DEFAULT)
    }
    fun setCriptoBase64(value: String?){
        cripto = Base64.decode(value,Base64.DEFAULT)
    }

    // Criptografia e decriptografia
    fun getClearText(): String?{
        return criptoGrafador.decipher(cripto!!)
    }
    fun setClearText(value: String?){
        cripto = criptoGrafador.cipher(value!!)
    }
}


class CriptoConverter {
    @TypeConverter
    fun fromCriptoString(value: CriptoString?): String? {
        return value?.getCriptoBase64()
    }

    @TypeConverter
    fun toCriptoString(value: String?): CriptoString? {
        val cripto = CriptoString()
        cripto.setCriptoBase64(value)
        return cripto
    }
}
