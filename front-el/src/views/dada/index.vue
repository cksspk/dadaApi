<template>

  <div class="app-container">
    <!-- 搜索栏 -->
    <el-form :inline="true" label-width="68px">
      <el-form-item label="订单编号">
        <el-input v-model="queryParams.id" placeholder="请输入订单编号" clearable size="small"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="收货人">
        <el-input v-model="queryParams.receiverName" placeholder="请输入收货人姓名" clearable size="small"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="queryParams.statusCode" placeholder="订单状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker v-model="dateRange" size="small" value-format="yyyy-MM-dd"
                        type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini"
                   @click="handleQuery">搜索
        </el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" icon="el-icon-plus" size="mini"
                   @click="handleAdd">新增
        </el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button type="success" icon="el-icon-edit" size="mini" :disabled="single"
                   @click="handleUpdate(null)">修改
        </el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button type="danger" icon="el-icon-delete" size="mini" :loading="delLoading" :disabled="multiple"
                   @click="handleDelete">删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" icon="el-icon-download" size="mini"
                   @click="handleExport">导出
        </el-button>
      </el-col>
    </el-row>
    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="list" @selection-change="handleSelectionChange">
      <el-table-column type="selection" align="center"/>
      <el-table-column label="订单编号" align="center" prop="id"/>
      <el-table-column label="订单价格(元)" align="center" prop="odPrice"/>
      <el-table-column label="收货人姓名" align="center" prop="receiverName"/>
      <el-table-column label="收货人地址" align="center" prop="receiverAddress"/>
      <el-table-column label="收货人号码" align="center" prop="receiverPhone"/>
      <el-table-column label="达达订单状态" align="center" prop="statusCode" :formatter="formatterStatus"/>
      <el-table-column label="达达运费(元)" align="center" prop="fee"/>
      <el-table-column label="骑手姓名" align="center" prop="dmName"/>
      <el-table-column label="骑手电话" align="center" prop="dmMobile"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="180" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!-- 查看订单详情 -->
          <!-- <el-popover trigger="click"  :ref="scope.row.id" placement="left" @show="showOrderDetail(scope.row.id)" @hide="hideOrderDetail(scope.row.id)" width="480" title="订单详情" >
            <el-button slot="reference" type="text" icon="el-icon-tickets" size="mini">详情
            </el-button>
          </el-popover>           -->
          <!-- 修改操作 -->
          <el-button size="mini" type="text" icon="el-icon-tickets"
                     @click="showOrderDetail(scope.row.id)">详情
          </el-button>
          <!-- 删除操作 -->
          <el-popover :ref="scope.row.id" placement="top" width="180">
            <p>确定删除本条数据吗？</p>
            <div style="text-align: right; margin: 0">
              <el-button size="mini" type="text" @click="$refs[scope.row.id].doClose()">取消
              </el-button>
              <el-button :loading="loading" type="primary" size="mini" @click="handleSubDelete(scope.row.id)">确定
              </el-button>
            </div>
            <el-button slot="reference" type="text" icon="el-icon-delete" size="mini">删除
            </el-button>
          </el-popover>
          <!-- 不使用重置操作 -->
          <!-- <el-button size="mini" type="text" icon="el-icon-key"
                     @click="handleResetPwd(scope.row)">重置
          </el-button> -->
          </template>
        </el-table-column>
      </el-table>
    <!-- 分页器 -->
    <pagination v-show="queryParams.total>0" :total="queryParams.total" :page.sync="queryParams.current" :limit.sync="queryParams.size"
                @pagination="getList"/>
                
    <!-- 添加或修改参数配置对话框 -->
   <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="open" width="700px">
      <el-form ref="form" :model="form"  :rules="orderRules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="订单价格" prop="odPrice">
              <el-input v-model="form.odPrice"  maxlength="11"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="收货人姓名" prop="receiverName">
              <el-input v-model="form.receiverName" placeholder="请输入收货人姓名" maxlength="50"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="收货人号码" prop="receiverPhone">
              <el-input v-model="form.receiverPhone" placeholder="请输入收货人号码"/>
            </el-form-item>
          </el-col>
           <el-col :span="12">
            <el-form-item label="收货地址" prop="receiverAddress">
              <el-autocomplete
                popper-class="my-autocomplete"
                v-model="form.receiverAddress"
                :fetch-suggestions="querySearch"
                placeholder="请输入收货地址"
                clearable
                style="width:100%"
                @select="handleSelect">
                <template slot-scope="{ item }">
                  <div class="name">{{ item.value }}</div>
                  <span class="addr">{{ item.address }}</span>
                </template>
              </el-autocomplete>
              </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 订单详情对话框 -->
    <el-dialog :close-on-click-modal="false" title="订单详情" :visible.sync="openDetail" width="700px">
      <el-form  v-loading= "detailLoading" ref="orderDetail" :model="orderDetail"  label-width="90px" class="orderDetail">
            <el-row>
              <el-col :span="12">
                <el-form-item label="骑手姓名：" prop="transporterName">
                  <span v-text="orderDetail.transporterName" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="骑手电话：" prop="transporterPhone">
                  <span v-text="orderDetail.transporterPhone" />
                </el-form-item>
              </el-col>
              <el-row>
                <!-- 左边展示电话信息 -->
                <el-col :span="12">
                  <el-col :span="24">
                    <el-form-item label="发单时间：" prop="createTime">
                      <span v-text="orderDetail.createTime" />
                    </el-form-item>
                  </el-col>
                  <el-col :span="24">
                    <el-form-item label="接单时间：" prop="acceptTime">
                      <span v-text="orderDetail.acceptTime" />
                    </el-form-item>
                  </el-col>
                  <el-col :span="24">
                    <el-form-item label="取货时间：" prop="fetchTime">
                      <span v-text="orderDetail.fetchTime" />
                    </el-form-item>
                  </el-col>
                  <el-col :span="24">
                    <el-form-item label="送达时间：" prop="finishTime">
                      <span v-text="orderDetail.finishTime" />
                    </el-form-item>
                  </el-col> 
                  <el-col :span="24">
                    <el-form-item label="收货码：" prop="orderFinishCode">
                      <span v-text="orderDetail.orderFinishCode" />
                    </el-form-item>
                  </el-col>
                </el-col>
                <!-- 右边展示地图信息 -->
                <el-col :span="12">
                  <!-- <el-amap vid="amapDemo" :center="mapCenter"  :zoom="16" :plugin="plugin"  class="amap-demo"> -->
                  <el-amap v-if="orderDetail.center" vid="transMap" :center="orderDetail.center"  :zoom="13" class="amap-demo">
                    <el-amap-marker :position="orderDetail.center" :title="orderDetail.transporterName"></el-amap-marker>
                  </el-amap>
                </el-col>
              </el-row>
            </el-row>
          </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary">确 定</el-button>
        <el-button @click="cancelDetail">取 消</el-button>
      </div>
    </el-dialog>
  </div>

</template>
 
<script>
import { list, getId as getOrderId, addOrder, delOrder, orderDetail} from '@/api/dada'
export default {

  data(){
    return {
      title: '',  //弹框的标题
      list: [], // 数据列表
      open: false,  // 是否显示弹出层
      openDetail:false,//详情弹出层
      form: {}, // 表单参数
      delLoading: false,  //删除按钮遮罩层
      loading : false,
      multiple: true, // 删除按钮非多个禁用
      single : true,  // 修改按钮非单个禁用
      ids: [],   // table选中数组
      row :{},   // 选中的行数据
      dateRange: [],// 日期范围
      //form校验规则
      orderRules: {
        receiverName: [{ required: true, trigger: 'blur', message: "收获人不能为空" }],
        receiverPhone: [{ required: true, trigger: 'blur', message: "收获人号码不能为空" }],
        receiverAddress: [{ required: true, trigger: 'blur', message: "地址不能为空" }],
      },
      // 状态下拉框
      statusOptions:[
            {dictValue : 1, dictLabel : '待接单', color: 'danger'},
            {dictValue : 2, dictLabel : '待取货', color: 'info'},
            {dictValue : 3, dictLabel : '配送中', color: 'danger'},
            {dictValue : 4, dictLabel : '已完成', color: 'success'},
            {dictValue : 5, dictLabel : '已取消', color: 'info'},
            {dictValue : 8, dictLabel : '指派单', color: 'info'},
            {dictValue : 9, dictLabel : '妥投异常之物品返回中', color: 'warning'},
            {dictValue : 10, dictLabel : '妥投异常之物品返回完成', color: 'success'},
            {dictValue : 100, dictLabel : '骑士到店', color: 'success'},
            {dictValue : 1000, dictLabel : '创建达达运单失败', color: 'danger'},
        ],
      //订单取消原因
      cancelFrom:{
        0:'未知',
        1:'达达配送员取消',
        2:'商家主动取消',
        3:'系统或客服取消',
      },
      //查询条件
      queryParams:{
        id: undefined,
        receiverName: undefined,
        statusCode: undefined,
        total: 0, // 总页数
        current: 1, // 当前页数
        size: 20, // 每页显示多少条
        ascs:[],//升序字段
        descs:"create_time"//降序字段
      },
      //订单详情
      orderDetail:{
          // orderId	String	第三方订单编号
          // statusCode	Integer	订单状态(待接单＝1,待取货＝2,配送中＝3,已完成＝4,已取消＝5, 指派单=8,妥投异常之物品返回中=9, 妥投异常之物品返回完成=10, 骑士到店=100,创建达达运单失败=1000 可参考文末的状态说明）
          // statusMsg	String	订单状态
          // transporterName	String	骑手姓名
          // transporterPhone	String	骑手电话
          // transporterLng	String	骑手经度
          // transporterLat	String	骑手纬度
          // deliveryFee	Double	配送费,单位为元
          // tips	Double	小费,单位为元
          // couponFee	Double	优惠券费用,单位为元
          // insuranceFee	Double	保价费,单位为元
          // actualFee	Double	实际支付费用,单位为元
          // distance	Double	配送距离,单位为米
          // createTime	String	发单时间
          // acceptTime	String	接单时间,若未接单,则为空
          // fetchTime	String	取货时间,若未取货,则为空
          // finishTime	String	送达时间,若未送达,则为空
          // cancelTime	String	取消时间,若未取消,则为空
          // orderFinishCode	String	收货码
          // deductFee	BigDecimal	违约金
      },
      detailLoading : false,
      
      center:[120.116803,30.297972],
    }
  },
  created(){
    this.getList()
  },
  methods :{
    //展示订单详情
    showOrderDetail(orderId){
      // console.log('点击查看订单详情,订单id：',orderId);
      
      this.detailLoading = true
      this.openDetail = true;

      orderDetail(orderId).then(resp=>{
        console.log('查询订单详情：',resp);
        if(resp.code === 200){
          this.orderDetail = resp.data
          //骑手的位置
          
          this.orderDetail.center= resp.data.transporterLng.length > 0 ? [Number(resp.data.transporterLng),Number(resp.data.transporterLat)] : undefined
        }
       this.detailLoading = false
      }).catch(err=>{
        this.detailLoading = false
      })
    },
    //关闭订单详情
    cancelDetail(){
      this.openDetail = false
    },
    /**
     * 根据关键字搜索位置信息
     */
    async querySearch(queryString, cb) {
      //1. 根据输入信息搜索地图  凤起农 景芳小区
      let resData = await this.queryAddress(queryString)
      // console.log('高德地图返回:',resData);
      let restaurants = []
      if(resData.status === "1"){
        //对返回的地理位置进行添加  
        restaurants = resData.tips.reduce((pre,item)=>{
          //当存在经纬度坐标进行添加
          if(item.location){
            pre.push({value: item.name, address: item.address, location: item.location,district: item.district})
          }
          return pre
        },[])
      }
      console.log('restaurants:',restaurants);
      cb(restaurants);
    },
    /**
    * 点击下拉框坐标
    */
    handleSelect(item) {
      const lng_lat = item.location.split(",")
      console.log('lng_lat',lng_lat);
      
      this.form.receiverLng = lng_lat[0] //经度
      this.form.receiverLat = lng_lat[1] //纬度
      this.form.receiverAddress = item.district + item.value
    },
    /**
     * 请求高德地图api
     */
    queryAddress(queryString){
      console.log('queryString:',queryString);
      
      //去除空格
      queryString = queryString.trim()
      if(queryString.length === 0){
        console.log('长度为0不进行搜索');
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
    /**
     * 查询分页数据
     */
    getList(){
      // console.log('查询达达订单');
      this.loading = true;
      list(Object.assign({beginTime:this.dateRange[0],endTime:this.dateRange[1]},this.queryParams)).then(resp=>{
          console.log('resp:',resp);
          if(resp.code === 200){
            // debugger
              this.list = resp.data.records
              this.queryParams.total = resp.data.total
              this.queryParams.size = resp.data.size
          }
           this.loading = false
      }).catch(err=>{
        this.loading = false
        // console.log('查询订单出错:',err);
      })
    },
    /**
     * 表格内容自定义展示 【订单状态】
     */
    formatterStatus(row, column){
        //订单状态(待接单＝1,待取货＝2,配送中＝3,已完成＝4,已取消＝5, 指派单=8,妥投异常之物品返回中=9, 妥投异常之物品返回完成=10, 骑士到店=100,创建达达运单失败=1000
        let tarData = this.statusOptions.filter(item=>item.dictValue==row.statusCode)[0]
        let resdata = JSON.parse(JSON.stringify(tarData)); 
        //当订单取消的时候显示 popover
        if(row.statusCode === 5){
          let value = this.cancelFrom[row.cancelFrom]
          return <el-popover placement="right-start" trigger="hover" >
                <div>
                  来源：{value}
                  <br/>
                  原因：{row.cancelReason}
                </div>
                <el-tag  type={resdata.color} slot="reference">{resdata.dictLabel}</el-tag>
              </el-popover> 
        }
        return <el-tag slot="reference" type={resdata.color}>{resdata.dictLabel}</el-tag>
    },
    /** 搜索查询 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList()
    },
    /**重置当前搜索框*/
    resetQuery() {
      this.dateRange = [];
      // total 在pagination组件中需要初始值Number
      this.queryParams={total:0};
      this.getList();
    },

    //根据id删除用户
    handleSubDelete(id){
      this.loading = true;
      delOrder(id).then(response=>{
        this.$refs[id].doClose();
        this.loading = false;
        if(response.code === 200){
          //删除成功
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.getList()
        }
      }).catch(()=>{
          //删除失败
          this.$refs[id].doClose();
          this.loading = false;
          this.$message({
              type: 'error',
              message: '删除失败'
          })
      })
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.row = selection[0];
      this.ids = selection.map(item => item.id);
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    //导出操作
    handleExport(){
      console.log('点击导出')
    },

    /**
     * 批量删除
     */
    handleDelete(){
      this.delLoading = true;
      const names = this.list.filter(item => this.ids.some(id=>id === item.id)).map(user=>user.userName)
      let $this = this;
      this.$confirm('是否确认删除主键为"' + $this.ids + '"的数据项?', "警告", {
        dangerouslyUseHTMLString : true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        delOrder($this.ids).then(response => {
          $this.delLoading = false;
          if (response.code === 200) {
            $this.getList()
            $this.msgSuccess("删除成功");
          } else {
            $this.msgError("删除失败");
          }
        }).catch(err => {
          $this.delLoading = false
        })
      }).catch(err => {
        $this.delLoading = false
      })
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加订单";
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.$nextTick(() => {
        if (this.$refs["form"] !== undefined) {
          this.$refs["form"].resetFields();
        }
      });
      
      //form初始化数据
      this.form = {odPrice: 18, receiverName: '张三' ,receiverPhone: 13285891995,receiverAddress:'',};
    },
    /** 修改按钮操作 不存在 */
    // handleUpdate(row) {
    //   row = row || this.row
    //   this.reset();
    // //   this.getRoles();
    //   getUser(row.id).then(response => {
    //     this.form = response.data;
    //     this.open = true;
    //     this.title = "修改用户";
    //   });
    // },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // console.log('提交表单：this.form',this.form)
            addOrder(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                 this.getList()
              } else {
                this.msgError(response.msg);
              }
            });
          }
      });
    },
  }
}
</script>

<style lang="scss" scoped>

.amap-demo {
  height: 200px;
  width: 300px;
}
//订单详情 中外边距
.orderDetail{
  .el-form-item{
    margin-bottom: 0;
  }
}
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