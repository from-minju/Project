const Sequelize = require('sequelize');

module.exports = class User extends Sequelize.Model {
    static init(sequelize) {
        return super.init({
            id: {
                type: Sequelize.STRING(20),
                allowNull: false,
                primaryKey: true
            },
            password: {
                type: Sequelize.STRING(5000),
                allowNull: false
            },
            name: {
                type: Sequelize.STRING(20),
                allowNull: false
            },
            addr: {
                type: Sequelize.STRING(100),
                allowNull: true
            },
            ph: {
                type: Sequelize.STRING(20),
                allowNull: true
            }

        }, {
            sequelize,
            timestamps: false,
            underscored: false,
            modelName: 'User',
            tableName: 'users',
            paranoid: false,
            charset: 'utf8mb4',
            collate: 'utf8mb4_general_ci',
        });
    }

    static associate(db) {
        db.User.hasMany(db.Order, { foreignKey: 'ou_id', sourceKey: 'id' });
    }
};
