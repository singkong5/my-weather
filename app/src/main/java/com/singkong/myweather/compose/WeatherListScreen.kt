package com.singkong.myweather.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.singkong.myweather.R
import com.singkong.myweather.data.HourlyWeatherLog
import com.singkong.myweather.data.LocationAndWeatherLogs
import com.singkong.myweather.ui.theme.cardBackground
import com.singkong.myweather.ui.theme.currentTempText
import com.singkong.myweather.ui.theme.highTempText
import com.singkong.myweather.ui.theme.titleText
import com.singkong.myweather.utilities.getWeatherDescriptionResourceId
import com.singkong.myweather.utilities.getWeatherIconResourceId
import java.util.Calendar

@Composable
fun WeatherListScreen(
   locationWeatherLogsList: List<LocationAndWeatherLogs>
) {
    if (locationWeatherLogsList.isEmpty()) {
        EmptyListScreen()
    } else {
        LazyColumn {
            items(items = locationWeatherLogsList, itemContent = {
                WeatherListItem(it)
            })
        }
    }
}

@Composable
fun WeatherListItem(locationAndWeatherLogs: LocationAndWeatherLogs) {

    ElevatedCard(
        modifier = Modifier
            .padding(
                start = dimensionResource(id = R.dimen.padding_small),
                end = dimensionResource(id = R.dimen.padding_small),
                top = dimensionResource(id = R.dimen.padding_small)
            )
            .fillMaxWidth(1F),
        colors = CardDefaults.cardColors(containerColor = cardBackground)
    ) {
        Column (
            modifier = Modifier.padding(
                all = dimensionResource(id = R.dimen.padding_small)
            )
        ) {
            Text(
                style = MaterialTheme.typography.titleLarge,
                color = titleText,
                text = locationAndWeatherLogs.location.name,
            )
            WeatherDataScreen(locationAndWeatherLogs.hourlyWeatherLogs)
        }
    }
}

@Composable
fun WeatherDataScreen(hourlyWeatherLogList: List<HourlyWeatherLog>) {

    val noData = hourlyWeatherLogList.isEmpty()
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

    Row {
        Image(
            painter = painterResource(id = if (noData) R.drawable.ic_default else getWeatherIconResourceId(hourlyWeatherLogList[currentHour].weatherCode)),
            contentDescription = stringResource(id = R.string.weather_icon_description),
            modifier = Modifier.size(dimensionResource(id = R.dimen.weather_icon_size), dimensionResource(id = R.dimen.weather_icon_size))
        )

        Column (
            modifier = Modifier.padding(
                all = dimensionResource(id = R.dimen.padding_small)
            )
        ) {
            Text(
                style = MaterialTheme.typography.titleMedium,
                color = titleText,
                text = stringResource(id = if (noData) R.string.not_available else getWeatherDescriptionResourceId(hourlyWeatherLogList[currentHour].weatherCode)),
            )

            Row (
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_xs))
            ) {
                Text(
                    style = MaterialTheme.typography.labelLarge,
                    color = currentTempText,
                    text = "${if (noData) "--" else hourlyWeatherLogList[currentHour].temperature}° C",
                )

                Column (
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.padding_large),
                        top = dimensionResource(id = R.dimen.padding_xs)
                    )
                ) {

                    //Max temp for the day
                    Text(
                        style = MaterialTheme.typography.labelSmall,
                        color = highTempText,
                        text = "H ${if (noData) "--" else hourlyWeatherLogList.maxBy { it.temperature}.temperature}° C",
                    )

                    //Min temp for the day
                    Text(
                        style = MaterialTheme.typography.labelSmall,
                        color = currentTempText,
                        text = "L  ${if (noData) "--" else hourlyWeatherLogList.minBy { it.temperature}.temperature}° C",
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyListScreen() {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.no_location_saved),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
    }
}


