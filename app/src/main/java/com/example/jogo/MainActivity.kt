package com.example.jogo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    private val frutas = listOf("manga", "cereja", "limão", "maçã", "banana", "abacate", "melancia",
        "kiwi", "morango", "açaí", "uva", "laranja")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                JogoDaFruta()
            }
        }
    }

    @Composable
    fun JogoDaFruta() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(android.graphics.Color.parseColor("#1F438c"))),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.conjunto_de_frutas),
                contentDescription = "Imagem contendo frutas que serão utilizadas no jogo de adivinhação",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = (76).dp)
                    .align(Alignment.TopCenter)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                var palpite by remember { mutableStateOf(TextFieldValue("")) }
                var mensagem by remember { mutableStateOf("") }
                var palpitesRestantes by remember { mutableStateOf(3) }
                var fruta by remember { mutableStateOf(frutas[Random.nextInt(frutas.size)]) }

                fun reiniciarJogo() {
                    palpite = TextFieldValue("")
                    mensagem = ""
                    palpitesRestantes = 3
                    Handler(Looper.getMainLooper()).postDelayed({
                        val novaFruta = frutas[Random.nextInt(frutas.size)]
                        fruta = novaFruta
                    }, 5000L)
                }

                OutlinedTextField(
                    value = palpite,
                    onValueChange = {
                        palpite = it
                    },
                    label = {
                        Text(
                            "Adivinhe uma das frutas acima.",
                            color = Color(android.graphics.Color.parseColor("#CEECF2"))
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(android.graphics.Color.parseColor("#F2E63D")),
                        unfocusedBorderColor = Color(android.graphics.Color.parseColor("#F2E63D"))
                    ),
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                Button(
                    onClick = {
                        if (palpite.text.isEmpty()) {
                            mensagem = "Por favor, digite uma fruta"
                        } else {
                            val palpiteUsuario = palpite.text.lowercase()
                            palpitesRestantes--

                            if (palpiteUsuario == fruta.lowercase()) {
                                mensagem = "Parabéns, você adivinhou a fruta!"
                                Handler(Looper.getMainLooper()).postDelayed({
                                    reiniciarJogo()
                                }, 5000L)
                            } else {
                                mensagem = "Palpite incorreto"
                                if (palpitesRestantes == 0) {
                                    mensagem += "\nVocê perdeu. A fruta era $fruta"
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        reiniciarJogo()
                                    }, 5000L)
                                } else {
                                    mensagem += "\nVocê tem $palpitesRestantes palpites restantes"
                                }
                            }

                            palpite = TextFieldValue("")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(
                            android.graphics.Color.parseColor(
                                "#F2E63D"
                            )
                        )
                    ),
                ) {
                    Text(
                        "Adivinhar",
                        color = Color(android.graphics.Color.parseColor("#0A0B0D"))
                    )
                }

                if (mensagem.isNotEmpty()) {
                    Text(
                        mensagem,
                        modifier = Modifier.padding(top = 16.dp),
                        color = Color(android.graphics.Color.parseColor("#CEECF2"))
                    )
                }
            }
            Image(
                painter = painterResource(id = R.drawable.mulher_com_frutas),
                contentDescription = "Imagem contendo mulher olhando de canto com frutas cabeça",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd)
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MaterialTheme {
            MainActivity()
        }
    }
}



