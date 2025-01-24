package com.example.tugasakhir.ui.viewpasien

import CostumeTopAppBar
import DetailPasienViewModel
import PasienDetailUiState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.model.Pasien
import com.example.tugasakhir.ui.navigation.DestinasiNavigasi
import com.example.tugasakhir.ui.viewmodel.PenyediaViewModel


object DestinasiDetailPasien : DestinasiNavigasi {
    override val route = "detail_pasien"//Base Route
    const val ID_PASIEN = "id_pasien"// Nama parameter untuk id pasien
    val routesWithArg = "$route/{$ID_PASIEN}"//Route yang menerima id pasien sebagai argumen
    override val titleRes = "Detail Pasien"//Title untuk halaman ini
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasienDetailView(
    idPasien: String,
    modifier: Modifier = Modifier,
    viewModel: DetailPasienViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (String) -> Unit = {},
    navigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailPasien.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getDetailPasien() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(idPasien) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Pasien"
                )
            }
        }
    ) { innerPadding ->
        val detailUiState by viewModel.pasienDetailUiState.collectAsState()

        BodyDetailPasien(
            modifier = Modifier.padding(innerPadding),
            detailUiState = detailUiState,
            retryAction = { viewModel.getDetailPasien() }
        )
    }
}

@Composable
fun BodyDetailPasien(
    modifier: Modifier = Modifier,
    detailUiState: PasienDetailUiState,
    retryAction: () -> Unit = {}
) {
    when (detailUiState) {
        is PasienDetailUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is PasienDetailUiState.Success -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailPasien(pasien = detailUiState.pasien)
            }
        }
        is PasienDetailUiState.Error -> {
            OnError(
                retryAction = retryAction,
                modifier = modifier.fillMaxSize()
            )
        }
        else -> {
            // Menangani kasus yang tidak terduga (optional, jika ingin menangani hal ini)
            // Bisa menambahkan logika untuk menangani kesalahan yang tidak diketahui
            Text("Unexpected state encountered")
        }
    }
}

@Composable
fun ItemDetailPasien(pasien: Pasien) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailPasien(judul = "ID Pasien", isinya = pasien.id_pasien.toString())
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPasien(judul = "Nama", isinya = pasien.nama)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPasien(judul = "Alamat", isinya = pasien.alamat)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPasien(judul = "Nomor Telepon", isinya = pasien.nomor_telepon)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPasien(judul = "Tanggal Lahir", isinya = pasien.tanggal_lahir)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPasien(judul = "Riwayat Medikal", isinya = pasien.riwayat_medikal)
        }
    }
}

@Composable
fun ComponentDetailPasien(
    judul: String,
    isinya: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul :",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
