/**
 *  Copyright 2017 Stelpro
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
 *  Stelpro Ki Thermostat
 *
 *  Author: Stelpro
 *
 *  Date: 2017-11-16
 */

preferences {
    input("zipcode", "text", title: "Code postal pour température extérieure", description: "[Ne pas laisser d'espace](Vide = Position du hub)")
    input("heatdetails", "enum", title: "Désirez-vous les notifications d'état de fonctionnement?", options: ["Non", "Oui"], 
    defaultValue: "Non", required: true, displayDuringSetup: true)
}
metadata {
    definition (name: "STZW402+", namespace: "stelpro", author: "Stelpro") {
        capability "Actuator"
        capability "Temperature Measurement"
        capability "Thermostat"
		capability "Configuration"
        capability "Polling"
        capability "Sensor"
        capability "Refresh"

        attribute "outsideTemp", "number"

        command "switchMode"
        command "quickSetHeat"
        command "quickSetOutTemp"
        command "increaseHeatSetpoint"
        command "decreaseHeatSetpoint"
        command "setCustomThermostatMode"
        command "eco"

        fingerprint deviceId: "0x0806", inClusters: "0x5E,0x86,0x72,0x40,0x43,0x31,0x85,0x59,0x5A,0x73,0x20,0x42"
    }

    // simulator metadata
    simulator {
        //Add test code here
    }

    tiles(scale : 1) 
    {
		multiAttributeTile(name:"mainPanel", type:"thermostat", 
			icon:"http://cdn.device-icons.smartthings.com/Home/home29-icn@2x.png",
			canChangeIcon: true, 
			inactiveLabel: true)  
		{
			tileAttribute("device.temperature", key: "PRIMARY_CONTROL") 
			{
				attributeState("temp", label:'${currentValue}', backgroundColors:[
					[value: 18, color: "#153591"],
					[value: 19, color: "#1e9cbb"],
					[value: 20, color: "#90d2a7"],
					[value: 21, color: "#44b621"],
					[value: 22, color: "#f1d801"],
					[value: 23, color: "#d04e00"],
					[value: 24, color: "#bc2323"]])
			}
		}
		multiAttributeTile(name:"detailsPanel", type:"thermostat", width:6, height:3) 
		{
			tileAttribute("device.temperature", key: "PRIMARY_CONTROL") 
			{
				attributeState("temp", label:'${currentValue}', backgroundColors:[
                    [value: 18, color: "#153591"],
                    [value: 19, color: "#1e9cbb"],
                    [value: 20, color: "#90d2a7"],
                    [value: 21, color: "#44b621"],
                    [value: 22, color: "#f1d801"],
                    [value: 23, color: "#d04e00"],
                    [value: 24, color: "#bc2323"]])
			}
			tileAttribute("device.thermostatOperatingState", key: "OPERATING_STATE") 
            {
				attributeState("idle", backgroundColor:"#44b621")
				attributeState("heating", backgroundColor:"#ffa81e")
			}
		}  
		multiAttributeTile(name:"consigne", type:"thermostat", width:2, height:2) 
        {
		   tileAttribute("device.heatingSetpoint", key: "PRIMARY_CONTROL") 
           {
			  attributeState("heatingSetpoint", label:'Consigne', backgroundColors:[
                 [value: 18, color: "#153591"],
                 [value: 19, color: "#1e9cbb"],
                 [value: 20, color: "#90d2a7"],
                 [value: 21, color: "#44b621"],
                 [value: 22, color: "#f1d801"],
                 [value: 23, color: "#d04e00"],
                 [value: 24, color: "#bc2323"]])
              attributeState("high", label:'HAUT')
              attributeState("low", label:'BAS')
              attributeState("--", label:'--')
		   }
           tileAttribute("device.heatingSetpoint", key: "VALUE_CONTROL") 
           {   
			  attributeState("VALUE_UP", action: "increaseHeatSetpoint")
              attributeState("VALUE_DOWN", action: "decreaseHeatSetpoint")
              attributeState("heatingSetpoint", label:'Consigne:\n${currentValue}', backgroundColors:[
                 [value: 18, color: "#153591"],
                 [value: 19, color: "#1e9cbb"],
                 [value: 20, color: "#90d2a7"],
                 [value: 21, color: "#44b621"],
                 [value: 22, color: "#f1d801"],
                 [value: 23, color: "#d04e00"],
                 [value: 24, color: "#bc2323"]])
		   }
		}  
        standardTile("mode", "device.thermostatMode", width: 2, height: 2) 
        {
		   state "heat", label:'${name}', action:"switchMode", nextState:"eco", icon:"http://cdn.device-icons.smartthings.com/Home/home29-icn@2x.png"
		   state "eco", label:'${name}', action:"switchMode", nextState:"heat", icon:"http://cdn.device-icons.smartthings.com/Outdoor/outdoor3-icn@2x.png"
        }
        standardTile("refresh", "device.refresh", decoration: "flat", width: 2, height: 2) 
        {
           state "default", action:"refresh.refresh", icon:"st.secondary.refresh", backgroundColor:"#f1d801"
        }
		main ("mainPanel")
        details(["detailsPanel", "consigne", "mode", "refresh"])
    }
}

def parse(String description)
{
	//if (description == "updated")
    	//return []

    //Class, version
    def map = createEvent(zwaveEvent(zwave.parse(description, [0x40:2, 0x43:2, 0x31:3, 0x42:1])))
    if (!map) {
        return null
    }

    def result = [map]
    if (map.isStateChange && map.name in ["heatingSetpoint","thermostatMode"]) {
        def map2 = [
                name: "thermostatSetpoint",
                unit: getTemperatureScale()
        ]
        if (map.name == "thermostatMode") {
            state.lastTriedMode = map.value
			map2.value = device.latestValue("heatingSetpoint")
        }
        else {
            def mode = device.latestValue("thermostatMode")
            log.info "THERMOSTAT, dernier mode = ${mode}"
            if (map.name == "heatingSetpoint") {
                map2.value = map.value
                map2.unit = map.unit
            }
        }
        if (map2.value != null) {
            log.debug "THERMOSTAT, ajout d'un évènement de consigne: $map"
            result << createEvent(map2)
        }
    }
    log.debug "Parse returned $result"
    result
}

// Event Generation
def zwaveEvent(physicalgraph.zwave.commands.thermostatsetpointv2.ThermostatSetpointReport cmd)
{
    def cmdScale = cmd.scale == 1 ? "F" : "C"
    def temp;
    float tempfloat;
    def map = [:]
    if (cmd.scaledValue >= 327)
    {
    	map.value = "--"
    }
    else
    {
        temp = convertTemperatureIfNeeded(cmd.scaledValue, cmdScale, cmd.precision)
        tempfloat = (Math.round(temp.toFloat() * 2)) / 2
        map.value = tempfloat
    }
    map.unit = getTemperatureScale()
    map.displayed = false
    switch (cmd.setpointType) {
        case 1:
            map.name = "heatingSetpoint"
            break;
        default:
            return [:]
    }
    // So we can respond with same format
    state.size = cmd.size
    state.scale = cmd.scale
    state.precision = cmd.precision
    //sendEvent(name:"heatingSetpoint", value:map.value)
    map
}

def zwaveEvent(physicalgraph.zwave.commands.sensormultilevelv3.SensorMultilevelReport cmd)
{
    def temp;
    float tempfloat;
    def format;
    def map = [:]
    if (cmd.sensorType == 1) {
        map.value = convertTemperatureIfNeeded(cmd.scaledSensorValue, cmd.scale == 1 ? "F" : "C", cmd.precision)
        map.unit = getTemperatureScale()
        map.name = "temperature"

        temp = map.value
        if (temp == "32765")		//0x7FFD
        {
            map.value = "bas"
        }
        else if (temp == "32767")	//0x7FFF
        {
            map.value = "haut"
        }
        else if (temp == "-32768")	//0x8000
        {
            map.value = "--"
        }
        else
        {
    		tempfloat = (Math.round(temp.toFloat() * 2)) / 2
    		map.value = tempfloat
        }

    } else if (cmd.sensorType == 5) {
        map.value = cmd.scaledSensorValue
        map.unit = "%"
        map.name = "humidité"
    }
    //sendEvent(name:"temperature", value:map.value)
    map
}

def zwaveEvent(physicalgraph.zwave.commands.thermostatoperatingstatev1.ThermostatOperatingStateReport cmd)
{
    def map = [:]
    switch (cmd.operatingState) {
        case physicalgraph.zwave.commands.thermostatoperatingstatev1.ThermostatOperatingStateReport.OPERATING_STATE_IDLE:
        map.value = "ralenti"
        break
        case physicalgraph.zwave.commands.thermostatoperatingstatev1.ThermostatOperatingStateReport.OPERATING_STATE_HEATING:
        map.value = "chauffage"
        break
    }
    map.name = "thermostatOperatingState"
    
    if (settings.heatdetails == "Non") {
    	map.displayed = false
    }
    
    map
}

def zwaveEvent(physicalgraph.zwave.commands.thermostatmodev2.ThermostatModeReport cmd) {
    def map = [:]
    switch (cmd.mode) {
        //Heat mode
		case physicalgraph.zwave.commands.thermostatmodev2.ThermostatModeReport.MODE_HEAT:
            map.value = "chauffage"
            break
        
		//Eco mode
		case '11':
            map.value = "eco"
            break
    }
    map.name = "thermostatMode"
    //sendEvent(name:"thermostatMode", value:map.value)
    map
}

def zwaveEvent(physicalgraph.zwave.commands.associationv2.AssociationReport cmd) {
	delayBetween([
    	zwave.associationV1.associationRemove(groupingIdentifier:1, nodeId:0).format(),
        zwave.associationV1.associationSet(groupingIdentifier:1, nodeId:[zwaveHubNodeId]).format(),
        poll()
    ], 2300)
}


def zwaveEvent(physicalgraph.zwave.commands.thermostatmodev2.ThermostatModeSupportedReport cmd) {
    log.debug "Évènement ZWAVE reçu: $cmd"
}

def zwaveEvent(physicalgraph.zwave.commands.basicv1.BasicReport cmd) {
    log.debug "Évènement ZWAVE reçu: $cmd"
}

def zwaveEvent(physicalgraph.zwave.Command cmd) {
    log.warn "Commande ZWAVE inattendue reçue $cmd"
}

// Command Implementations
def poll() {
    def weather

    // If there is a zipcode defined, weather forecast will be sent. Otherwise, no weather forecast.
    if (settings.zipcode) {
        log.debug "Code postal: ${settings.zipcode}"
        weather = getWeatherFeature( "conditions", settings.zipcode )

        // Check if the variable is populated, otherwise return.
        if (!weather) {
            log.debug( "Erreur, aucune donnée reçue." )
            return false
        }

        // Set the tiles
        def locationScale = getTemperatureScale()
        def tempToSend
        if (locationScale == "C")
        {
            log.debug( "Temperature extérieure: ${weather.current_observation.temp_c}ºC" )
            sendEvent( name: 'outsideTemp', value: weather.current_observation.temp_c )
            tempToSend = weather.current_observation.temp_c
        }
        else
        {
            log.debug( "Temperature extérieure: ${weather.current_observation.temp_f}ºF" )
            sendEvent( name: 'outsideTemp', value: weather.current_observation.temp_f )
            tempToSend = weather.current_observation.temp_f
        }


        delayBetween([
                quickSetOutTemp(tempToSend),
                zwave.thermostatOperatingStateV1.thermostatOperatingStateGet().format(),
                zwave.thermostatModeV2.thermostatModeGet().format(),
                zwave.thermostatSetpointV2.thermostatSetpointGet(setpointType: 1).format(),
                zwave.sensorMultilevelV3.sensorMultilevelGet().format(), // current temperature
                sendEvent( name: 'change', value: 0 )
        ], 100)
    } else {
        delayBetween([
                zwave.thermostatOperatingStateV1.thermostatOperatingStateGet().format(),
                zwave.thermostatModeV2.thermostatModeGet().format(),
                zwave.thermostatSetpointV2.thermostatSetpointGet(setpointType: 1).format(),
                zwave.sensorMultilevelV3.sensorMultilevelGet().format(), // current temperature
                sendEvent( name: 'change', value: 0 )
        ], 100)
    }
}

def refresh() {
	poll()
}

def configure() {
	poll()
}

def quickSetHeat(degrees) {
    setHeatingSetpoint(degrees, 0)
}

def setHeatingSetpoint(preciseDegrees, delay = 0) {
	def degrees = new BigDecimal(preciseDegrees).setScale(1, BigDecimal.ROUND_HALF_UP)
	log.trace "setHeatingSetpoint($degrees, $delay)"
    def deviceScale = state.scale ?: 1
	def deviceScaleString = deviceScale == 2 ? "C" : "F"
    def locationScale = getTemperatureScale()
	def p = (state.precision == null) ? 1 : state.precision

    def convertedDegrees
    if (locationScale == "C" && deviceScaleString == "F") {
        convertedDegrees = celsiusToFahrenheit(degrees)
    } else if (locationScale == "F" && deviceScaleString == "C") {
        convertedDegrees = fahrenheitToCelsius(degrees)
    } else {
        convertedDegrees = degrees
    }
	
	sendEvent(name: "heatingSetpoint", value: degrees, unit: locationScale)

    delayBetween([
            zwave.thermostatSetpointV2.thermostatSetpointSet(setpointType: 1, scale: deviceScale, precision: p, scaledValue: convertedDegrees).format(),
			zwave.thermostatSetpointV2.thermostatSetpointGet(setpointType: 1).format(),
            poll()
    ], 1000)
}

def quickSetOutTemp(degrees) {
    setOutdoorTemperature(degrees, 0)
}

def setOutdoorTemperature(degrees, delay = 0) {
    setOutdoorTemperature(degrees.toDouble(), delay)
}

def setOutdoorTemperature(Double degrees, Integer delay = 0) {
    def deviceScale
    def locationScale = getTemperatureScale()
    def p = (state.precision == null) ? 1 : state.precision

    if (locationScale == "C")
    {
        deviceScale = 0
    }
    else
    {
        deviceScale = 1
    }
    log.info "setOutdoorTemperature: ${degrees}"
    zwave.sensorMultilevelV3.sensorMultilevelReport(sensorType: 1, scale: deviceScale, precision: p,  scaledSensorValue: degrees).format()
}
/*
def increaseHeatSetpoint()
{
	float currentSetpoint = device.currentValue("heatingSetpoint")
	def locationScale = getTemperatureScale()
	float maxSetpoint
	float step

	if (locationScale == "C")
	{
		maxSetpoint = 30;
		step = 0.5
	}
	else
	{
		maxSetpoint = 86
		step = 1
	}

	if (currentSetpoint < maxSetpoint)
	{
		currentSetpoint = currentSetpoint + step
		quickSetHeat(currentSetpoint)
	}
}

def decreaseHeatSetpoint()
{
	float currentSetpoint = device.currentValue("heatingSetpoint")
	def locationScale = getTemperatureScale()
	float minSetpoint
	float step

	if (locationScale == "C")
	{
		minSetpoint = 5;
		step = 0.5
	}
	else
	{
		minSetpoint = 41
		step = 1
	}

	if (currentSetpoint > minSetpoint)
	{
		currentSetpoint = currentSetpoint - step
		quickSetHeat(currentSetpoint)
    }
}
*/

def increaseHeatSetpoint()
{
	def params = [
    //uri: "http://192.168.129.105:8080/json.htm?type=command&param=switchlight&idx=90&switchcmd=On",
    uri: "https://www.ibm.com/ca-fr/",
    path: "/get"
]

try {
    httpGet(params) { resp ->
        // iterate all the headers
        // each header has a name and a value
        resp.headers.each {
           log.debug "${it.name} : ${it.value}"
        }

        // get an array of all headers with the specified key
        def theHeaders = resp.getHeaders("Content-Length")

        // get the contentType of the response
        log.debug "response contentType: ${resp.contentType}"

        // get the status code of the response
        log.debug "response status code: ${resp.status}"

        // get the data from the response body
        log.debug "response data: ${resp.data}"
    }
} catch (e) {
    log.error "something went wrong: $e"
}
}

def decreaseHeatSetpoint()
{
def params = [
    uri: "http://192.168.129.105:8080/json.htm?type=command&param=switchlight&idx=90&switchcmd=Off",
    path: "/get"
]

try {
    httpGet(params) { resp ->
        // iterate all the headers
        // each header has a name and a value
        resp.headers.each {
           log.debug "${it.name} : ${it.value}"
        }

        // get an array of all headers with the specified key
        def theHeaders = resp.getHeaders("Content-Length")

        // get the contentType of the response
        log.debug "response contentType: ${resp.contentType}"

        // get the status code of the response
        log.debug "response status code: ${resp.status}"

        // get the data from the response body
        log.debug "response data: ${resp.data}"
    }
} catch (e) {
    log.error "something went wrong: $e"
}
}


def switchMode() {
    def currentMode = device.currentState("thermostatMode")?.value
  	def lastTriedMode = state.lastTriedMode ?: currentMode ?: "heat"
  	def modeOrder = modes()
  	def next = { modeOrder[modeOrder.indexOf(it) + 1] ?: modeOrder[0] }
  	def nextMode = next(lastTriedMode)
  	state.lastTriedMode = nextMode
    delayBetween([
        zwave.thermostatModeV2.thermostatModeSet(mode: modeMap[nextMode]).format(),
    	zwave.thermostatModeV2.thermostatModeGet().format()
  ], 1000)
}

def modes() {
    ["heat", "eco"]
}

def getModeMap() { [
        "heat": 1,
        "eco": 11,
]}

def getDataByName(String name) {
    state[name] ?: device.getDataValue(name)
}

def setCoolingSetpoint(coolingSetpoint) {
    log.trace "${device.displayName} ne supporte pas un point de consigne de refroidissement"
}

def heat() {
	log.trace "mode chauffage en marche"
	delayBetween([
		zwave.thermostatModeV2.thermostatModeSet(mode: 1).format(),
		zwave.thermostatModeV2.thermostatModeGet().format()
	], 1000)
}

def eco() {
	log.trace "mode ECO en marche"
	delayBetween([
		zwave.thermostatModeV2.thermostatModeSet(mode: 11).format(),
		zwave.thermostatModeV2.thermostatModeGet().format()
	], 1000)
}

def off() {
    log.trace "${device.displayName} ne supporte pas le mode arrêt"
}

def auto() {
    log.trace "${device.displayName} ne supporte pas le mode auto"
}

def emergencyHeat() {
    log.trace "${device.displayName} ne supporte pas le mode de chauffage d'urgence"
}

def cool() {
    log.trace "${device.displayName} ne supporte pas le mode de refroidissement"
}

def setCustomThermostatMode(mode) {
   setThermostatMode(mode)
}

def setThermostatMode(String value) {
	delayBetween([
		zwave.thermostatModeV2.thermostatModeSet(mode: modeMap[value]).format(),
		zwave.thermostatModeV2.thermostatModeGet().format()
	], 1000)
}

def fanOn() {
    log.trace "${device.displayName} ne supporte pas le mode ventilateur"
}

def fanAuto() {
    log.trace "${device.displayName} ne supporte pas le mode ventilateur auto"
}

def fanCirculate() {
    log.trace "${device.displayName} ne supporte pas le mode ventilateur circulaire"
}

def setThermostatFanMode() {
    log.trace "${device.displayName} ne supporte pas le mode ventilateur"
}