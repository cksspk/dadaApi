import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import './assets/styles/element-variables.scss'
import 'element-ui/lib/theme-chalk/index.css'
// import locale from 'element-ui/lib/locale/lang/en' // lang i18n
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n

import '@/styles/index.scss' // global css
import '@/assets/styles/index.scss' // global css
import '@/assets/styles/dimple.scss' // 自定义样式

//引入地图
import VueAMap from "vue-amap";

import App from './App'
import router from './router'
import store from './store'

import '@/icons' // icon
import '@/permission' // permission control

import {parseTime, resetForm, addDateRange, selectDictLabel} from "@/utils/util";
//分页组件
import Pagination from "@/components/Pagination";

import Axios from 'axios'
//配置Axios
Vue.prototype.$axios = Axios;

// 全局方法挂载
Vue.prototype.parseTime = parseTime;
Vue.prototype.resetForm = resetForm;
Vue.prototype.addDateRange = addDateRange;
Vue.prototype.selectDictLabel = selectDictLabel;

//全局提示组件
Vue.prototype.msgSuccess = function (msg) {
  this.$message({showClose: true, message: msg, type: "success"});
};

Vue.prototype.msgError = function (msg) {
  this.$message({showClose: true, message: msg, type: "error"});
};

Vue.prototype.msgWarning = function (msg) {
  this.$message({showClose: true, message: msg, type: "warning"});
};

Vue.prototype.msgInfo = function (msg) {
  this.$message.info(msg);
};

// 全局组件挂载
Vue.component('Pagination', Pagination);

Vue.use(ElementUI, { locale })

Vue.config.productionTip = false

//配置高德地图插件
Vue.use(VueAMap);
VueAMap.initAMapApiLoader({
  key: 'f4105542e4b15efd31ca073c082d04d1',
  plugin: [
    "AMap.Autocomplete", //输入提示插件
    "AMap.PlaceSearch", //POI搜索插件
    "AMap.Scale", //右下角缩略图插件 比例尺
    "AMap.OverView", //地图鹰眼插件
    "AMap.ToolBar", //地图工具条
    "AMap.MapType", //类别切换控件，实现默认图层与卫星图、实施交通图层之间切换的控制
    "AMap.PolyEditor", //编辑 折线多，边形
    "AMap.CircleEditor", //圆形编辑器插件
    "AMap.Geolocation" //定位控件，用来获取和展示用户主机所在的经纬度位置
  ],
  v: '1.4.4'
});


new Vue({
  el: '#app',
  router, //挂载路由
  store,
  render: h => h(App)
})
