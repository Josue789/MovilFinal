package com.example.mynotes.Multimedia

import android.net.Uri
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.mynotes.R

// DIALOGOS PARA MOSTRAR LA MULTIMEDIA
@Composable
fun DialogShowAudioSelected(
    onDismiss: () -> Unit,
    fileUri: List<Uri?>
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card {
            Column(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(0.7f)
                    .padding(PaddingValues(16.dp))
                    .horizontalScroll(rememberScrollState())
            ) {
                fileUri.map {
                    AudioPlayer(
                        audioUri = it
                    )
                }

                Spacer(modifier = Modifier.size(16.dp))

                Button(
                    onClick = {
                        onDismiss()
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = stringResource(id = R.string.btnAceptar))
                }
            }
        }
    }
}


@Composable
fun DialogShowVideoTake(
    onDismiss: () -> Unit,
    videoUri: List<Uri?>
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card {
            Column(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(0.7f)
                    .padding(PaddingValues(16.dp))
            ) {
                Row(Modifier.horizontalScroll(rememberScrollState())){
                    videoUri.map {
                        VideoPlayer(
                            videoUri = it,
                            modifier = Modifier
                                .size(250.dp)
                        )}
                }

                Spacer(modifier = Modifier.size(16.dp))

                Button(
                    onClick = {
                        onDismiss()
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = stringResource(id = R.string.btnAceptar))
                }
            }
        }
    }
}

@Composable
fun DialogShowImageTake(
    onDismiss: () -> Unit,
    imageUri: List<Uri>
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card {
            Column(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(0.7f)
                    .padding(PaddingValues(16.dp))
            ) {
                Row(Modifier
                    .horizontalScroll(rememberScrollState())) {
                imageUri.map {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth()
                )}
                }

                Spacer(modifier = Modifier.size(16.dp))

                // guardar uri
                Button(
                    onClick = {
                        onDismiss()
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = stringResource(id = R.string.btnAceptar))
                }
            }
        }
    }
}

@Composable
fun DialogShowImagesInEdit(
    onDismiss: () -> Unit,
    imageUri: String
){
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card {
            Column(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(0.7f)
                    .padding(PaddingValues(16.dp))
            ) {
                var list = imageUri.split(" ")

                Row(Modifier
                    .horizontalScroll(rememberScrollState())) {

                    list[1].split("|").map {
                        if (it.isNotEmpty()){
                            val uri = Uri.parse(it)
                            AsyncImage(
                                model = uri,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxHeight(0.5f)
                                    .fillMaxWidth()
                            )}
                        }
                    }

                Spacer(modifier = Modifier.size(16.dp))

                // guardar uri
                Button(
                    onClick = {
                        onDismiss()
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = stringResource(id = R.string.btnAceptar))
                }
            }
        }
    }
}

@Composable
fun DialogShowFileSelected(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card {
            Column(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(0.7f)
                    .padding(PaddingValues(16.dp))
            ) {
                Text(text = "Documento PDF cargado con exito.")

                Spacer(modifier = Modifier.size(16.dp))

                Button(
                    onClick = {
                        onDismiss()
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = stringResource(id = R.string.btnAceptar))
                }
            }
        }
    }
}