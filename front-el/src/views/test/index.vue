<template>
 <div >
  <el-autocomplete
    popper-class="my-autocomplete"
    v-model="state"
    :fetch-suggestions="querySearch"
    placeholder="请输入内容"
    @select="handleSelect">
    <i
      class="el-icon-close el-input__icon"
      slot="suffix"
      style="cursor:pointer"
      @click="handleIconClick">
    </i>
    <template slot-scope="{ item }">
      <div class="name">{{ item.value }}</div>
      <span class="addr">{{ item.address }}</span>
    </template>
  </el-autocomplete>
  <br>
    <h3>选择的位置：{{tarAddress}}</h3>
</div>
</template>

<script>
  export default {
    data() {
      return {
        state: '',
        tarAddress: null,
        restaurants:[]
      };
    },
    methods: {
      async querySearch(queryString, cb) {
        //1. 根据输入信息搜索地图  凤起农 景芳小区
        let resData = await this.queryAddress(queryString)
        console.log('高德地图返回:',resData);
        let restaurants = []
        if(resData.status === "1"){
          //对返回的地理位置进行添加  
          restaurants = resData.tips.reduce((pre,item)=>{
            //当存在经纬度坐标进行添加
            if(item.location){
              pre.push({value: item.name, address: item.address, location: item.location})
            }
            // console.log('pre',pre);
            return pre
          },[])
        }
        console.log('restaurants:',restaurants);
        
    
        // 直接返回高德建议数据，不用在进行匹配输入关键字
        // var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
        // console.log('results:',results);
        
        // 调用 callback 返回建议列表的数据,  
        cb(restaurants);
      },
      // createFilter(queryString) {
      //   return (restaurant) => {
      //     return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      //   };
      // },
     
     /**
      * 点击下拉框坐标
      */
      handleSelect(item) {
        const lng_lat = item.location.split(",")
        console.log('lng_lat',lng_lat);
        this.tarAddress = {
          receiver_address: item.address,
          receiver_lng: lng_lat[0], //经度
          receiver_lat: lng_lat[1], //纬度
        }
        // console.log(item);
      },

      /**
       * 点击图标清空输入框
       */
      handleIconClick(ev) {
        console.log('点击清空输入框数据：',this.state);
        this.state = ''
        // console.log(ev);
      },

      /**
       * 根据关键字搜索位置信息
       */
      queryAddress(queryString){
        //去除空格
        queryString = queryString.trim()
        if(queryString.length === 0){
          // console.log('长度为0不进行搜索');
          return {}
        }
        //根据关键字搜索POI
        return new Promise((resolve, reject) => {
          const params = {
            key: 'f5748f7a747808eac6c140cb5ac1b2a5',  //key
            keywords: queryString,
            city: '杭州',         //查询城市
            citylimit: true,  //仅返回指定城市数据
          }
          this.$axios.get('https://restapi.amap.com/v3/assistant/inputtips',{params}).then(response=>{
            resolve(response.data)
          }).catch(err=>{
            reject(err)
          })
        })
      },

    },
    mounted() {
      
    }
  }
</script>

<style lang="scss">
.my-autocomplete {
  li {
    line-height: normal;
    padding: 7px;

    .name {
      text-overflow: ellipsis;
      overflow: hidden;
    }
    .addr {
      font-size: 12px;
      color: #b4b4b4;
    }

    .highlighted .addr {
      color: #ddd;
    }
  }
}
</style>