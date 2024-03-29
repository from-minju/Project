const Sequelize = require('sequelize');
const User = require('./user');
const Order = require('./order');
const Product = require('./product');

const env = process.env.NODE_ENV || 'development';
const config = require('../config/config')[env];
const db = {};

const sequelize = new Sequelize(
    config.database, config.username, config.password, config
);

db.sequelize = sequelize;

db.User = User;
db.Order = Order;
db.Product = Product;

User.init(sequelize);
Order.init(sequelize);
Product.init(sequelize);



User.associate(db);
Order.associate(db);
Product.associate(db);

module.exports = db;


