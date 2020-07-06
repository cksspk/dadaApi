import request from '@/utils/request'

// 分页查询达达所有记录
export function list(query) {
  return request({
    url: '/vant/dada/list',
    method: 'get',
    params: query
  })
}

/**
 * 添加订单
 */
export function addOrder(data) {
  return request({
    url: '/vant/dada',
    method: 'post',
    data: data
  })
}

/**
 *删除订单 
 */
export function delOrder(id) {
  return request({
    url: '/vant/dada/' + id,
    method: 'delete'
  })
}

/**
 *订单详情 
 */
export function orderDetail(id) {
  return request({
    url: '/vant/dada/detail/' + id,
    method: 'get'
  })
}

