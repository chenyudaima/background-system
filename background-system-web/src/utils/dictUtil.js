import http from '@/utils/http.js'
async function getDictData(dictTypeId) {
  let data = []
  await http.get("/home/system/sysDict/dataAll",{params: {dictTypeId: dictTypeId}}).then(resp => {
    if(resp.code == 200) {
      data = resp.data
    }
  })
  return data;
}


export default
  {
    getDictData
  }