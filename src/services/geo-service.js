const axios = require('axios');
const { mapQuestKey } = require('../config');

async function getCoords(location) {
  const mapQuestUrl = `https://www.mapquestapi.com/geocoding/v1/address?key=${mapQuestKey}&location=${location}`;
  const response = await axios.get(mapQuestUrl);
  const { lng: lon, lat } = response.data.results[0].locations[0].latLng;
  return { lon, lat };
}

module.exports = {
  getCoords,
};
