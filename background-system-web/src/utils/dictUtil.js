import http from '@/utils/http.js'
async function getDictData(dictTypeId) {
  let data = []
  await http.get("/home/system/sysDict/dataAll",{params: {dictTypeId: dictTypeId}}).then(resp => {
    data = resp.data
  })
  return data;
}


export default
  {
    getDictData
  }