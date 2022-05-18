const express = require('express');
const { Order, Product } = require('../models');
const { isLoggedIn, isNotLoggedIn } = require('./middlewares');

const router = express.Router();

router.get('/buy/:id', isLoggedIn, async(req, res, next) => { //buy/productId

    res.locals.user = req.user; 

    const order = await Order.findOne({ 
        where: {ou_id: req.user.id}
    });
    const product = await Product.findOne({
        where: {id: req.params.id}
    });

    res.render('buy', {
            title: require('../package.json').name,
            port: process.env.PORT,
            order: order,
            product: product
    });
});


router.post('/buy/:id', async(req, res, next) => {
    res.locals.user = req.user; 

    const { cnt, addr } = req.body;

    var today = new Date();
    var year = today.getFullYear();
    var month = ('0' + (today.getMonth() + 1)).slice(-2);
    var day = ('0' + today.getDate()).slice(-2);
    var hours = ('0' + today.getHours()).slice(-2);
    var minutes = ('0' + today.getMinutes()).slice(-2);
    var seconds = ('0' + today.getSeconds()).slice(-2);
    var orderID = req.params.id + req.user.id + year + month + day + hours + minutes + seconds;

    //:id를 사용자의 Order에 추가
    const order = await Order.create({
        id: orderID, //고유한 주문id생성
        ou_id: req.user.id,
        op_id: req.params.id,
        cnt,
        addr
    });

    const product = await Product.findOne({
        where: {id: req.params.id}
    });

    const totalPrice = order.cnt * product.price;
    
    res.render('sumPay', {
        order, product, totalPrice,
        title: require('../package.json').name,
        port: process.env.PORT
    })

});


module.exports = router;
