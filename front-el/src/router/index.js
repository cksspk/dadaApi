import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
 * Note: 路由配置项
 *
 * hidden: true                   // 当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
 * alwaysShow: true               // 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
 *                                // 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
 *                                // 若你想不管路由下面的 children 声明的个数都显示你的根路由
 *                                // 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
 * redirect: noRedirect           // 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
 * name:'router-name'             // 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
 * meta : {
    roles: ['admin','editor']    // 设置该路由进入的权限，支持多个权限叠加
    title: 'title'               // 设置该路由在侧边栏和面包屑中展示的名字
    icon: 'svg-name'             // 设置该路由的图标，对应路径src/icons/svg
    breadcrumb: false            // 如果设置为false，则不会在breadcrumb面包屑中显示
  }
 */
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },
// 首页
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard',
    // name: '首页',
    // meta: {title :'首页'},
    // hidden: true,
    children: [{
      path: 'dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },
  //达达订单管理
  {
    path: '/orderManage',
    component: Layout,
    redirect: '/orderManage/orderList',
    // redirect: 'noRedirect',
    name: '订单管理',
    meta: { title: '订单管理', icon: 'example' },
    children: [
      {
        path: 'orderList',
        name: '订单列表',
        component: () => import('@/views/dada/index'),
        meta: { title: '订单管理', icon: 'component ' }
      },
    ]
  },
  // //测试页面
  // {
  //   path: '/test',
  //   component: Layout,
  //   redirect: '/test',
  //   // redirect: 'noRedirect',
  //   name: '测试',
  //   meta: { title: '测试管理', icon: 'example' },
  //   children: [
  //     {
  //       path: 'test1',
  //       name: 'table测试',
  //       component: () => import('@/views/test/index'),
  //       meta: { title: 'table测试', icon: 'component ' }
  //     },
  //   ]
  // },
  {
    path: '/map',
    component: Layout,
    redirect: '/map',
    // redirect: 'noRedirect',
    name: '地图相关',
    meta: { title: '地图相关', icon: 'example' },
    children: [
      {
        path: 'querySearch/',
        name: '关键字搜索',
        component: () => import('@/views/map/querySearch'),
        meta: { title: '关键字搜索', icon: 'component ' }
      },
      {
        path: 'mapClick/',
        name: '地图搜索',
        component: () => import('@/views/map/mapClick'),
        meta: { title: '地图搜索', icon: 'component ' }
      },
    ]
  },
  //系统管理
  // {
  //   path: '/system',
  //   component: Layout,
  //   redirect: '/system/user',
  //   // name: '系统管理',
  //   meta: { title: '系统管理', icon: 'example' },
  //   children: [
  //     { 
  //       path: 'user',
  //       name: '用户管理',
  //       component: () => import('@/views/system/user/index'),
  //       meta: { title: '用户管理', icon: 'user' }
  //     },
  //     {
  //       path: 'role',
  //       name: '角色管理',
  //       component: () => import('@/views/system/role/index'),
  //       meta: { title: '角色管理', icon: 'peoples' }
  //     },
  //     {
  //       path: 'menu',
  //       name: '菜单管理',
  //       component: () => import('@/views/system/menu/index'),
  //       meta: { title: '菜单管理', icon: 'tree-table' }
  //     },
  //     {
  //       path: 'test',
  //       name: '测试',
  //       component: () => import('@/views/test/index'),
  //       meta: { title: '测试', icon: 'tree-table' }
  //     }
  //   ]
  // },
  
  
  {
    path: '/example',
    component: Layout,
    redirect: '/example/table',
    name: 'Example',
    meta: { title: 'Example', icon: 'example' },
    children: [
      {
        path: 'table',
        name: 'Table',
        component: () => import('@/views/table/index'),
        meta: { title: 'Table', icon: 'table' }
      },
      {
        path: 'tree',
        name: 'Tree',
        component: () => import('@/views/tree/index'),
        meta: { title: 'Tree', icon: 'tree' }
      }
    ]
  },

  {
    path: '/form',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import('@/views/form/index'),
        meta: { title: 'Form', icon: 'form' }
      }
    ]
  },

  {
    path: '/nested',
    component: Layout,
    redirect: '/nested/menu1',
    name: 'Nested',
    meta: {
      title: 'Nested',
      icon: 'nested'
    },
    children: [
      {
        path: 'menu1',
        component: () => import('@/views/nested/menu1/index'), // Parent router-view
        name: 'Menu1',
        meta: { title: 'Menu1' },
        children: [
          {
            path: 'menu1-1',
            component: () => import('@/views/nested/menu1/menu1-1'),
            name: 'Menu1-1',
            meta: { title: 'Menu1-1' }
          },
          {
            path: 'menu1-2',
            component: () => import('@/views/nested/menu1/menu1-2'),
            name: 'Menu1-2',
            meta: { title: 'Menu1-2' },
            children: [
              {
                path: 'menu1-2-1',
                component: () => import('@/views/nested/menu1/menu1-2/menu1-2-1'),
                name: 'Menu1-2-1',
                meta: { title: 'Menu1-2-1' }
              },
              {
                path: 'menu1-2-2',
                component: () => import('@/views/nested/menu1/menu1-2/menu1-2-2'),
                name: 'Menu1-2-2',
                meta: { title: 'Menu1-2-2' }
              }
            ]
          },
          {
            path: 'menu1-3',
            component: () => import('@/views/nested/menu1/menu1-3'),
            name: 'Menu1-3',
            meta: { title: 'Menu1-3' }
          }
        ]
      },
      {
        path: 'menu2',
        component: () => import('@/views/nested/menu2/index'),
        meta: { title: 'menu2' }
      }
    ]
  },

  {
    path: 'external-link',
    component: Layout,
    children: [
      {
        path: 'https://panjiachen.github.io/vue-element-admin-site/#/',
        meta: { title: 'External Link', icon: 'link' }
      }
    ]
  },

  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
