/**
*  Copyright 2015 SmartThings
*
*  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License. You may obtain a copy of the License at:
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
*  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
*  for the specific language governing permissions and limitations under the License.
*
*  SmartWeather Station
*
*  Author: SmartThings
*
*  Date: 2013-04-30
*/
metadata {
    definition (name: "Toile meteo 1.0", namespace: "jproy1957", author: "Takis") {
        capability "Illuminance Measurement"
        capability "Temperature Measurement"
        capability "Relative Humidity Measurement"
        capability "Sensor"
        capability "Polling"

        attribute "localSunrise", "string"
        attribute "localSunset", "string"
        attribute "city", "string"
        attribute "timeZoneOffset", "string"
        attribute "weather", "string"
        attribute "wind", "string"
        attribute "winddirection", "string"
        attribute "wind_gust", "string"
        attribute "winddirection_deg", "string"
        attribute "windinfo", "string"
        attribute "uv_index", "string"
        attribute "water", "string"
        attribute "weatherIcon", "string"
        attribute "forecastIcon", "string"
        attribute "feelsLike", "string"
        attribute "percentPrecip", "string"
        attribute "percentPrecipToday", "string"
        attribute "percentPrecipLastHour", "string"
        attribute "alert", "string"
        attribute "alertKeys", "string"
        attribute "sunriseDate", "string"
        attribute "sunsetDate", "string"
        attribute "dewpoint", "string"
        attribute "wind_degrees", "string"
        attribute "pressure", "string"
        attribute "solarradiation", "string"
        attribute "visibility", "string"
        attribute "pressureTrend", "string"
        
        command "refresh"
    }

    preferences {
        input "zipCode", "text", title: "Code postal (optionnel)", required: false
        
        input ("pres_units", "enum", title: "Unités de pression barométrique (optionnel)", required: false, 
			options: [
		        "press_in":"Pouces",
		        "press_mb":"Millibars"
            ])
        input ("dist_units", "enum", title: "Unités de distance (optionnel)", required: false, 
			options: [
		        "dist_mi":"Miles",
		        "dist_km":"Kilomètres"
            ])
        input("height_units", "enum", title: "Unités d'hauteur (optionnel)", required: false, 
			options: [
                "height_in":"Pouces",
                "height_mm":"Millimètres"
            ])
        input("speed_units", "enum", title: "Unités de vitesse (optionnel)", required: false, 
			options: [
                "speed_mph":"Miles par heure",
                "speed_kph":"Kilomètres par heure"
            ])
        input "weather", "device.smartweatherStationTile", title: "Météo...", multiple: true, required: false
    }
    
    tiles(scale: 2) 
    {
        multiAttributeTile(name:"temperature", type:"generic", width:6, height:4, canChangeIcon: false) {
            tileAttribute("device.temperature", key: "PRIMARY_CONTROL") {
                attributeState("temperature",label:'${currentValue}°C',
					backgroundColors:[
					[value: -8, color: "#153591"],
					[value: 0, color: "#1e9cbb"],
					[value: 10, color: "#90d2a7"],
					[value: 21, color: "#44b621"],
					[value: 24, color: "#f1d801"],
					[value: 26, color: "#d04e00"],
					[value: 28, color: "#bc2323"]])
            }
            tileAttribute("device.feelsLike", key: "SECONDARY_CONTROL") {
                attributeState("default", label:'Ressentie ${currentValue}°C')
            }
        }    
        standardTile("weatherIcon", "device.weatherIcon", decoration: "flat") {
            state "chanceflurries", icon:"st.custom.wu1.chanceflurries", label: ""
            state "chancerain", icon:"st.custom.wu1.chancerain", label: ""
            state "chancesleet", icon:"st.custom.wu1.chancesleet", label: ""
            state "chancesnow", icon:"st.custom.wu1.chancesnow", label: ""
            state "chancetstorms", icon:"st.custom.wu1.chancetstorms", label: ""
            state "clear", icon:"st.custom.wu1.clear", label: ""
            state "cloudy", icon:"st.custom.wu1.cloudy", label: ""
            state "flurries", icon:"st.custom.wu1.flurries", label: ""
            state "fog", icon:"st.custom.wu1.fog", label: ""
            state "hazy", icon:"st.custom.wu1.hazy", label: ""
            state "mostlycloudy", icon:"st.custom.wu1.mostlycloudy", label: ""
            state "mostlysunny", icon:"st.custom.wu1.mostlysunny", label: ""
            state "partlycloudy", icon:"st.custom.wu1.partlycloudy", label: ""
            state "partlysunny", icon:"st.custom.wu1.partlysunny", label: ""
            state "rain", icon:"st.custom.wu1.rain", label: ""
            state "sleet", icon:"st.custom.wu1.sleet", label: ""
            state "snow", icon:"st.custom.wu1.snow", label: ""
            state "sunny", icon:"st.custom.wu1.sunny", label: ""
            state "tstorms", icon:"st.custom.wu1.tstorms", label: ""
            state "cloudy", icon:"st.custom.wu1.cloudy", label: ""
            state "partlycloudy", icon:"st.custom.wu1.partlycloudy", label: ""
            state "nt_chanceflurries", icon:"st.custom.wu1.nt_chanceflurries", label: ""
            state "nt_chancerain", icon:"st.custom.wu1.nt_chancerain", label: ""
            state "nt_chancesleet", icon:"st.custom.wu1.nt_chancesleet", label: ""
            state "nt_chancesnow", icon:"st.custom.wu1.nt_chancesnow", label: ""
            state "nt_chancetstorms", icon:"st.custom.wu1.nt_chancetstorms", label: ""
            state "nt_clear", icon:"st.custom.wu1.nt_clear", label: ""
            state "nt_cloudy", icon:"st.custom.wu1.nt_cloudy", label: ""
            state "nt_flurries", icon:"st.custom.wu1.nt_flurries", label: ""
            state "nt_fog", icon:"st.custom.wu1.nt_fog", label: ""
            state "nt_hazy", icon:"st.custom.wu1.nt_hazy", label: ""
            state "nt_mostlycloudy", icon:"st.custom.wu1.nt_mostlycloudy", label: ""
            state "nt_mostlysunny", icon:"st.custom.wu1.nt_mostlysunny", label: ""
            state "nt_partlycloudy", icon:"st.custom.wu1.nt_partlycloudy", label: ""
            state "nt_partlysunny", icon:"st.custom.wu1.nt_partlysunny", label: ""
            state "nt_sleet", icon:"st.custom.wu1.nt_sleet", label: ""
            state "nt_rain", icon:"st.custom.wu1.nt_rain", label: ""
            state "nt_sleet", icon:"st.custom.wu1.nt_sleet", label: ""
            state "nt_snow", icon:"st.custom.wu1.nt_snow", label: ""
            state "nt_sunny", icon:"st.custom.wu1.nt_sunny", label: ""
            state "nt_tstorms", icon:"st.custom.wu1.nt_tstorms", label: ""
            state "nt_cloudy", icon:"st.custom.wu1.nt_cloudy", label: ""
            state "nt_partlycloudy", icon:"st.custom.wu1.nt_partlycloudy", label: ""
        }
        valueTile("lastSTupdate", "device.lastSTupdate", inactiveLabel: false, width: 2, height: 1, decoration: "flat", wordWrap: true) {
            state("default", label: 'Dernière mise-à-jour\n ${currentValue}')
        }
        valueTile("humidity", "device.humidity", inactiveLabel: false, width: 2, height: 1, decoration: "flat", wordWrap: true) {
            state "default", label:'Humidité ${currentValue}%', unit:"%"
        }
        valueTile("weather", "device.weather", inactiveLabel: false, width: 1, height: 1, decoration: "flat", wordWrap: true) {
            state "default", label:'${currentValue}'
        }
        valueTile("percentPrecip", "device.percentPrecip", inactiveLabel: false, width: 1, height: 1, decoration: "flat", wordWrap: true) {
            state "default", label:'Pluie\n ${currentValue}%'
        }
        valueTile("percentPrecipToday", "device.percentPrecipToday", inactiveLabel: false, width: 2, height: 1, decoration: "flat", wordWrap: true) {
            state "default", label:'Pluie aujourd\'hui\n ${currentValue}'
        }
        valueTile("percentPrecipLastHour", "device.percentPrecipLastHour", inactiveLabel: false, width: 2, height: 1, decoration: "flat", wordWrap: true) {
            state "default", label:'Pluie dernière heure\n ${currentValue}'
        }
        standardTile("refresh", "device.weather", inactiveLabel: false, width: 1, height: 1, decoration: "flat", wordWrap: true) {
            state "default", label: "", action: "refresh", icon:"st.secondary.refresh"
        }
        valueTile("alert", "device.alert", inactiveLabel: false, width: 5, height: 1, decoration: "flat", wordWrap: true) {
            state "default", label:'Alertes météo:\n ${currentValue}'
        }
        valueTile("rise", "device.localSunrise", inactiveLabel: false, width: 2, height: 1, decoration: "flat", wordWrap: true) {
            state "default", label:'Lever du soleil\n ${currentValue}'
        }
        valueTile("set", "device.localSunset", inactiveLabel: false, width: 2, height: 1, decoration: "flat", wordWrap: true) {
            state "default", label:'Coucher du soleil\n ${currentValue}'
        }
        valueTile("light", "device.illuminance", inactiveLabel: false, width: 2, height: 1, decoration: "flat", wordWrap: true) {
            state "default", label:'${currentValue} lux'
        }
        valueTile("visibility", "device.visibility", inactiveLabel: false, width: 2, height: 1, decoration: "flat", wordWrap: true) {
            state "default", label:'Visibilité\n ${currentValue}'
        }
        valueTile("uv_index", "device.uv_index", inactiveLabel: false, width: 2, height: 1, decoration: "flat", wordWrap: true) {
            state "uv_index", label: 'Index UV ${currentValue}', unit: "UV Index"
        }
        standardTile("water", "device.water", inactiveLabel: false, width: 1, height: 1, decoration: "flat", wordWrap: true) {
            state "default", label: 'Mise-à-jour...', icon: "st.unknown.unknown.unknown"
            state "true",        icon: "st.alarm.water.wet",        backgroundColor:"#ff9999"
            state "false",       icon: "st.alarm.water.dry",        backgroundColor:"#99ff99"
        }
        valueTile("dewpoint", "device.dewpoint", inactiveLabel: false, width: 2, height: 1, decoration: "flat", wordWrap: true) {
            state "default", label:'Condensation ${currentValue}°C'
        }
        valueTile("pressure", "device.pressure", inactiveLabel: false, width: 3, height: 1, decoration: "flat", wordWrap: true) {
            state "pressure", label: 'Pression ${currentValue}'
        }
        valueTile("windinfo", "device.windinfo", inactiveLabel: false, width: 3, height: 1, decoration: "flat", wordWrap: true) {
            state "windinfo", label: 'Vent ${currentValue}'
        }
        valueTile("temperature2", "device.temperature", width: 1, height: 1, canChangeIcon: true) {
            state "temperature", label: '${currentValue}°C .. ${device.windinfo}', foregroundColors:"#000000",
				backgroundColors:[
				[value: -8, color: "#153591"],
				[value: 0, color: "#1e9cbb"],
				[value: 10, color: "#90d2a7"],
				[value: 21, color: "#44b621"],
				[value: 24, color: "#f1d801"],
				[value: 26, color: "#d04e00"],
				[value: 28, color: "#bc2323"]
            ]
        }
        
        main(["temperature2"])
        details(["temperature", "feelslike", "weatherIcon", "weather", "humidity" , "dewpoint", "windinfo", 
        "pressure", "solarradiation", "uv_index", "light", "visibility", "city", "rise", "set", "lastSTupdate", 
        "percentPrecip", "percentPrecipToday", "percentPrecipLastHour", "water", "alert", "refresh"])
    }
}

// parse events into attributes
def parse(String description) {
    log.debug "Parsing '${description}'"
}

def installed() {
	runEvery15Minutes(poll)
}

def uninstalled() {
	unschedule()
}

def updated() {
	unschedule()
    runEvery15Minutes(poll)
}

// handle commands
def poll() {
    log.debug "WUSTATION: Executing 'poll', location: ${location.name}"

    // Current conditions
    def obs = get("conditions")?.current_observation
    if (obs) {
        log.debug "obs --> ${obs}"
        def now = new Date().format('HH:mm:ss yyyy.M.d',location.timeZone)
        sendEvent(name:"lastSTupdate", value: now)
        
        def weatherIcon = obs.icon_url.split("/")[-1].split("\\.")[0]

        if(getTemperatureScale() == "C") {
            send(name: "temperature", value: Math.round(obs.temp_c), unit: "C")
            send(name: "feelsLike", value: Math.round(obs.feelslike_c as Double), unit: "C")
            send(name: "dewpoint", value: Math.round(obs.dewpoint_c as Double), unit: "C")
        } else {
            send(name: "temperature", value: Math.round(obs.temp_f), unit: "F")
            send(name: "feelsLike", value: Math.round(obs.feelslike_f as Double), unit: "F")
            send(name: "dewpoint", value: Math.round(obs.dewpoint_f as Double), unit: "F")
        }
        
        send(name: "humidity", value: obs.relative_humidity[0..-2] as Integer, unit: "%")
        send(name: "weather", value: obs.weather)
        send(name: "weatherIcon", value: weatherIcon, displayed: false)
        
        send(name: "uv_index", value: obs.UV)
        send(name: "solarradiation", value: obs.solarradiation, display: false)
        
        
        def pressure_trend_text
        switch (obs.pressure_trend) {
        case "-" :
            pressure_trend_text = "Baisse"
            break;

        case "+":
            pressure_trend_text = "Hausse"
            break;
        case "=":
            pressure_trend_text = "Stable"
            break;
        case "0":
            pressure_trend_text = "Aucun changement"
            break;
        default:
            pressure_trend_text = "N/D"
        }
        
        if (pres_units) {
            switch (pres_units) {
            case "press_mb" :
                send(name: "pressure", value: "${obs.pressure_mb} mbar (${pressure_trend_text})")
                break;

            case "press_in":
                send(name: "pressure", value: "${obs.pressure_in} pouces (${pressure_trend_text})")
                break;
            default:
                send(name: "pressure", value: "${obs.pressure_mb} mbar (${pressure_trend_text})")
            }
        } else {
            send(name: "pressure", value: "${obs.pressure_mb} mbar (${pressure_trend_text})")
        }        

        if (dist_units) {
            switch (dist_units) {
            case "dist_mi" :
                send(name: "visibility", value: "${obs.visibility_mi} mi")
                break;

            case "dist_km":
                send(name: "visibility", value: "${obs.visibility_km} km")
                break;
            default:
                send(name: "visibility", value: "${obs.visibility_km} km")
            }
        } else {
            send(name: "visibility", value: "${obs.visibility_km} km")
        }      
        
        if (height_units) {
            switch (height_units) {
            case "height_in" :
                send(name: "percentPrecipToday", value: "${obs.precip_today_in} in")
                send(name: "percentPrecipLastHour", value: "${obs.precip_1hr_in} in")
                break;

            case "height_mm":
                send(name: "percentPrecipToday", value: "${obs.precip_today_metric} mm")
                send(name: "percentPrecipLastHour", value: "${obs.precip_1hr_metric} mm")
                break;
            default:
                send(name: "percentPrecipToday", value: "${obs.precip_today_metric} mm")
                send(name: "percentPrecipLastHour", value: "${obs.precip_1hr_metric} mm")
            }
        } else {
            send(name: "percentPrecipToday", value: "${obs.precip_today_metric} mm")
            send(name: "percentPrecipLastHour", value: "${obs.precip_1hr_metric} mm")
        }  

        if (speed_units) {
            switch (speed_units) {
            case "speed_mph" :
                send(name: "windinfo", value: "${obs.wind_dir} (${obs.wind_degrees}°) à ${obs.wind_mph} mph\n(Gust: ${obs.wind_gust_mph} mph)")
                send(name: "wind_gust", value: "${obs.wind_gust_mph}")
                send(name: "winddirection", value: "${obs.wind_dir}")
                send(name: "winddirection_deg", value: "${obs.wind_degrees}")
                send(name: "wind", value: "${obs.wind_mph}")
                break;
            case "speed_kph":
                send(name: "windinfo", value: "${obs.wind_dir} (${obs.wind_degrees}°) à ${obs.wind_kph} kmh\n(Bourasque: ${obs.wind_gust_kph} kmh)")
                send(name: "wind_gust", value: "${obs.wind_gust_kph}")
                send(name: "winddirection", value: "${obs.wind_dir}")
                send(name: "winddirection_deg", value: "${obs.wind_degrees}")
                send(name: "wind", value: "${obs.wind_kph}")
                break;
            default:
                send(name: "windinfo", value: "${obs.wind_dir} (${obs.wind_degrees}°) à ${obs.wind_kph} kmh\n(Bourasque: ${obs.wind_gust_kph} kmh)")
                send(name: "wind_gust", value: "${obs.wind_gust_kph}")
                send(name: "winddirection", value: "${obs.wind_dir}")
                send(name: "winddirection_deg", value: "${obs.wind_degrees}")
                send(name: "wind", value: "${obs.wind_kph}")
            }
        } else {
            send(name: "windinfo", value: "${obs.wind_dir} (${obs.wind_degrees}°) à ${obs.wind_kph} kmh\n(Bourasque: ${obs.wind_gust_kph} kmh)")
        }                  
        
        // Since precip_1hr_in is a string, we need to convert it to a decimal in order to compare it as a number.
        if (obs.precip_1hr_in.toFloat() > 0) {
            sendEvent( name: 'water', value: "true" )
        } else {
            sendEvent( name: 'water', value: "false" )
        }

        if (obs.local_tz_offset != device.currentValue("timeZoneOffset")) {
            send(name: "timeZoneOffset", value: obs.local_tz_offset, isStateChange: true)
        }

        def cityValue = "${obs.display_location.city}, ${obs.display_location.state}"
        if (cityValue != device.currentValue("city")) {
            send(name: "city", value: cityValue, isStateChange: true)
        }

        // Sunrise / sunset
        def a = get("astronomy")?.moon_phase
        def today = localDate("GMT${obs.local_tz_offset}")
        def ltf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm")
        ltf.setTimeZone(TimeZone.getTimeZone("GMT${obs.local_tz_offset}"))
        def utf = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        utf.setTimeZone(TimeZone.getTimeZone("GMT"))

        def sunriseDate = ltf.parse("${today} ${a.sunrise.hour}:${a.sunrise.minute}")
        def sunsetDate = ltf.parse("${today} ${a.sunset.hour}:${a.sunset.minute}")

        def tf = new java.text.SimpleDateFormat("HH:mm")
        tf.setTimeZone(TimeZone.getTimeZone("GMT${obs.local_tz_offset}"))
        def localSunrise = "${tf.format(sunriseDate)}"
        def localSunset = "${tf.format(sunsetDate)}"
        send(name: "localSunrise", value: localSunrise, descriptionText: "Lever du soleil à $localSunrise")
        send(name: "localSunset", value: localSunset, descriptionText: "Coucher du soleil à $localSunset")

        send(name: "illuminance", value: estimateLux(sunriseDate, sunsetDate, weatherIcon))

        // Forecast
        def f = get("forecast")
        def f1= f?.forecast?.simpleforecast?.forecastday
        if (f1) {
            def icon = f1[0].icon_url.split("/")[-1].split("\\.")[0]
            def value = f1[0].pop as String // as String because of bug in determining state change of 0 numbers
            send(name: "percentPrecip", value: value, unit: "%")
            send(name: "forecastIcon", value: icon, displayed: false)
        }
        else {
            log.warn "Forecast not found"
        }

        // Alerts
        def alerts = get("alerts")?.alerts
        def newKeys = alerts?.collect{it.type + it.date_epoch} ?: []
        //log.debug "WUSTATION: newKeys = $newKeys"
        log.trace device.currentState("alertKeys")
        def oldKeys = device.currentState("alertKeys")?.jsonValue
        //log.debug "WUSTATION: oldKeys = $oldKeys"

        def noneString = "Aucune alerte météo"
        if (!newKeys && oldKeys == null) {
            send(name: "alertKeys", value: newKeys.encodeAsJSON(), displayed: false)
            send(name: "alert", value: noneString, descriptionText: "Aucune alert météo à ${device.displayName}", isStateChange: true)
        }
        else if (newKeys != oldKeys) {
            if (oldKeys == null) {
                oldKeys = []
            }
            send(name: "alertKeys", value: newKeys.encodeAsJSON(), displayed: false)

            def newAlerts = false
            alerts.each {alert ->
                if (!oldKeys.contains(alert.type + alert.date_epoch)) {
                    def msg = "${alert.description} de ${alert.date} jusqu\'à ${alert.expires}"
                    send(name: "alert", value: pad(alert.description), descriptionText: msg, isStateChange: true)
                    newAlerts = true
                }
            }

            if (!newAlerts && device.currentValue("alert") != noneString) {
                send(name: "alert", value: noneString, descriptionText: "Aucune alert météo à ${device.displayName}", isStateChange: true)
            }
        }
    }
    else {
        log.warn "No response from Weather Underground API"
    }
}

def refresh() {
    poll()
}

def configure() {
    poll()
}

private pad(String s, size = 25) {
    def n = (size - s.size()) / 2
    if (n > 0) {
        def sb = ""
        n.times {sb += " "}
        sb += s
        n.times {sb += " "}
        return sb
    }
    else {
        return s
    }
}


private get(feature) {
    getWeatherFeature("${feature}/lang:FR", zipCode)
}

private localDate(timeZone) {
    def df = new java.text.SimpleDateFormat("yyyy-MM-dd")
    df.setTimeZone(TimeZone.getTimeZone(timeZone))
    df.format(new Date())
}

private send(map) {
    //log.debug "WUSTATION: event: $map"
    sendEvent(map)
}

private estimateLux(sunriseDate, sunsetDate, weatherIcon) {
    def lux = 0
    def now = new Date().time
    if (now > sunriseDate.time && now < sunsetDate.time) {
        //day
        switch(weatherIcon) {
        case 'tstorms':
            lux = 200
            break
        case ['cloudy', 'fog', 'rain', 'sleet', 'snow', 'flurries',
            'chanceflurries', 'chancerain', 'chancesleet',
            'chancesnow', 'chancetstorms']:
            lux = 1000
            break
        case 'mostlycloudy':
            lux = 2500
            break
        case ['partlysunny', 'partlycloudy', 'hazy']:
            lux = 7500
            break
        default:
            //sunny, clear
            lux = 10000
        }

        //adjust for dusk/dawn
        def afterSunrise = now - sunriseDate.time
        def beforeSunset = sunsetDate.time - now
        def oneHour = 1000 * 60 * 60

        if(afterSunrise < oneHour) {
            //dawn
            lux = (long)(lux * (afterSunrise/oneHour))
        } else if (beforeSunset < oneHour) {
            //dusk
            lux = (long)(lux * (beforeSunset/oneHour))
        }
    }
    else {
        //night - always set to 10 for now
        //could do calculations for dusk/dawn too
        lux = 10
    }

    lux
}
