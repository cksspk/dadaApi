<template>
 <div >
   <h1>地图点击</h1>
  <br>
  <h3>选择的位置：{{tarAddress}}</h3>
    
    {{mapCenter}}
  <!-- 分割线 -->
  <el-divider></el-divider>
  <!-- 地图插件 -->
  <template>
      <div class="amap-page-container">
        <el-amap-search-box class="search-box" :search-option="searchOption" :on-search-result="onSearchResult"></el-amap-search-box>
        <el-amap vid="amapDemo" :center="mapCenter"  :zoom="16" :plugin="plugin"  class="amap-demo">
          <el-amap-marker :position="mapCenter" :title="markerTitle"></el-amap-marker>
        </el-amap>
      </div>
  </template>
</div>
</template>

<script>
  export default {
    data() {
      let self = this;
      return {
        state: '',
        tarAddress: null,
        restaurants:[],
        markerTitle:"",
        //高德地图使用amap
        searchOption: {
          city: '杭州',
          citylimit: true
        },
        mapCenter: [120.116803,30.297972],
        //插件
        plugin: [{
          pName: 'Geolocation',
          events: {
            init(o) {
              // o 是高德地图定位插件实例
              o.getCurrentPosition((status, result) => {
                if (result && result.position) {
                  self.markerTitle = result.formattedAddress
                  self.lng = result.position.lng;
                  self.lat = result.position.lat;
                  self.mapCenter = [self.lng, self.lat];
                  self.loaded = true;
                  self.$nextTick();
                }
              });
            }
          }
        },
        ]
      };
    },
    methods: {
      // 高德amap
      addMarker: function() {
          let lng = 121.5 + Math.round(Math.random() * 1000) / 10000;
          let lat = 31.197646 + Math.round(Math.random() * 500) / 10000;
          this.markers.push([lng, lat]);
        },
      onSearchResult(pois) {
        console.log('onSearchResult:',pois);
        
        // let latSum = 0;
        // let lngSum = 0;
        if (pois.length > 0) {
          // pois.forEach(poi => {
          //   let {lng, lat} = poi;
          //   lngSum += lng;
          //   latSum += lat;
          //   this.markers.push([poi.lng, poi.lat]);
          // });
          // let center = {
          //   lng: lngSum / pois.length,
          //   lat: latSum / pois.length
          // };
          //位置选择
          // this.mapCenter = [center.lng, center.lat];
          this.markerTitle = pois[0].name
          this.mapCenter = [pois[0].location.lng, pois[0].location.lat];
          console.log('this.mapCenter',this.mapCenter);
        }
      }


    },
    mounted() {
      
    }
  }
</script>

<style lang="scss">
.amap-demo {
  height: 300px;
  width: 1000px;
}
.search-box {
  position: absolute !important;
  top: 10px;
  left: 20px;
}
.amap-page-container {
  position: relative;
  height: 300px;
}

</style>