package com.singkong.myweather.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.singkong.myweather.R
import com.singkong.myweather.data.repo.TemperatureUnit
import com.singkong.myweather.data.repo.UserPreferences
import com.singkong.myweather.viewmodels.SettingsViewModel

@Composable
fun SettingsDialog(
    onDismiss: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val configuration = LocalConfiguration.current
    val userPreferences by viewModel.userPreferences.observeAsState(initial = UserPreferences(0, TemperatureUnit.CELSIUS))

    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.widthIn(max = configuration.screenWidthDp.dp - dimensionResource(id = R.dimen.settings_dialog_width_margin)),
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = stringResource(id = R.string.settings),
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            Divider()
            Column(Modifier.verticalScroll(rememberScrollState())) {
                SettingsPanel(userPreferences = userPreferences, onTemperatureUnitChanged = viewModel::updateTemperatureUnit)
                Divider(Modifier.padding(top = dimensionResource(id = R.dimen.padding_small)))
            }
        },
        confirmButton = {
            Text(
                text = stringResource(R.string.ok_button),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
                    .clickable { onDismiss() },
            )
        },
    )
}

@Composable
private fun SettingsPanel(
    userPreferences: UserPreferences,
    onTemperatureUnitChanged: (unit: TemperatureUnit) -> Unit
) {
    SettingsDialogSectionTitle(text = stringResource(id = R.string.units_title))
    Column(Modifier.selectableGroup()) {
        SettingsDialogThemeChooserRow(
            text = stringResource(R.string.temperature_unit_celsius),
            selected = userPreferences.temperatureUnit == TemperatureUnit.CELSIUS,
            onClick = { onTemperatureUnitChanged(TemperatureUnit.CELSIUS) },
        )
        SettingsDialogThemeChooserRow(
            text = stringResource(R.string.temperature_unit_fahrenheit),
            selected = userPreferences.temperatureUnit == TemperatureUnit.FAHRENHEIT,
            onClick = { onTemperatureUnitChanged(TemperatureUnit.FAHRENHEIT) },
        )
    }
}

@Composable
private fun SettingsDialogSectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_large), bottom = dimensionResource(id = R.dimen.padding_small)),
    )
}

@Composable
fun SettingsDialogThemeChooserRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
        )
        Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_small)))
        Text(text)
    }
}