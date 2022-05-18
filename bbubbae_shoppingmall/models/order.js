const Sequelize = require('sequelize');

module.exports = class Order extends Sequelize.Model {
    static init(sequelize) {
        return super.init({
            id: {
                type: Sequelize.STRING(500),
                allowNull: false,
                primaryKey: true
            },
            cnt: {
                type: Sequelize.INTEGER,
                allowNull: false
            },
            addr:{
                type:Sequelize.STRING(100),
                allowNull: false
            }

        }, {
            sequelize,
            timestamps: false,
            underscored: false,
            modelName: 'Order',
            tableName: 'orders',
            paranoid: false,
            charset: 'utf8mb4',
            collate: 'utf8mb4_general_ci',
        });
    }

    static associate(db) {
        db.Order.belongsTo(db.Product, {foreignKey: 'op_id', targetKey: 'id'});

        db.Order.belongsTo(db.User, {foreignKey: 'ou_id', targetKey: 'id'});
    }
};