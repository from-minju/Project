const express = require('express');
const { Product } = require('../models');
const router = express.Router();


router.get('/', async(req, res, next) => {
        res.locals.user = req.user; 

        try {
                const products = await Product.findAll({
                        attributes: ['id', 'name', 'price', 'img']
                });

                res.render('product', {
                        title: require('../package.json').name,
                        port: process.env.PORT,
                        products: products
                });
        } catch(err) {
                console.error(err);
                next(err);
        }
});

router.get('/:id', async(req, res, next) => {
        res.locals.user = req.user; 
        try{
                const product = await Product.findAll({
                        where: {id: req.params.id}
                });

                res.render('productDetail', {
                        title: require('../package.json').name,
                        port: process.env.PORT,
                        product: product
                })
        } catch(err) {
                console.error(err);
                next(err);
        }
});




module.exports = router;
