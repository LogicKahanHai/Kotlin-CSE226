module.exports = (sequelize, DataTypes) => {
  return sequelize.define("Teacher", {
    name: DataTypes.STRING,
    uid: { type: DataTypes.STRING, unique: true }, // Unique Identifier for Teacher
    sections: { type: DataTypes.JSON }, // Store sections as a list of strings
  });
};
