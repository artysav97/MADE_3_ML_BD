package test

import breeze.linalg.Vector.castOps
import breeze.linalg._
import breeze.stats.mean
import java.io._

object main {
  def main(args: Array[String]): Unit = {

    def split(data : DenseMatrix[Double], test_size : Double): (DenseMatrix[Double], DenseMatrix[Double], DenseVector[Double], DenseVector[Double]) = {
      var data_train: DenseMatrix[Double] = data(0 to 0, ::)
      var data_test: DenseMatrix[Double] = data(1 to 1, ::)
      val r = scala.util.Random

      for (i <- 2 to data.rows-1) {
        if (r.nextDouble > test_size) {
          data_train = DenseMatrix.vertcat(data_train, data(i to i, ::))
        }
        else {
          data_test = DenseMatrix.vertcat(data_test, data(i to i, ::))
        }
      }
      val X_tr = data_train(::, 0 to -2)
      val y_tr = data_train(::, -1)
      val X_ts = data_test(::, 0 to -2)
      val y_ts = data_test(::, -1)

      println(s"Train shape: ${X_tr.rows}")
      println(s"Test shape: ${X_ts.rows}")

      return (X_tr, X_ts, y_tr, y_ts)
    }


    class LinearRegression (gamma : Double, n_iter : Int, n_MSE_show : Int) {
      var weights = DenseVector.ones[Double](6)
      var intercept: Double = 1

      def predict (X: DenseMatrix[Double]) : Vector[Double] = (X * weights) + DenseVector.fill[Double](X.rows){intercept}
      def MSE (X: DenseMatrix[Double], y: DenseVector[Double]): Double = mean((y - predict(X)).map(x => x*x))

      def gradient_weights (X: DenseMatrix[Double], y: DenseVector[Double]) : DenseVector[Double] = (X.t * (y - predict(X=X))).map(x => -2 * x / X.rows)
      def gradient_intercept (X: DenseMatrix[Double], y: DenseVector[Double]) : Double = -2 * mean(y - predict(X=X))

      def update_weights(X: DenseMatrix[Double], y: DenseVector[Double]): Unit = {
        weights :+= gradient_weights(X, y).map(x => -1 * gamma * x)
        intercept = intercept - 1 * gradient_intercept(X, y)
      }

      def fit(X: DenseMatrix[Double], y: DenseVector[Double]): Unit = {
        weights = DenseVector.ones[Double](X.cols)
        for (i <- 0 to n_iter) {
          update_weights(X, y)
          if (i % (n_iter/n_MSE_show) == 0) {
            println(s"Step ${i}/${n_iter}: MSE = ${MSE(X, y)}")
          }
        }
      }
    }

    val data = csvread(new File("C:\\Users\\artys\\IdeaProjects\\My_Linear_Regression\\Fish_data.csv"),',', skipLines = 1)
    val (train_X, test_X, train_y, test_y) : (DenseMatrix[Double], DenseMatrix[Double], DenseVector[Double], DenseVector[Double]) = split(data=data, test_size = 0.3)
    val lr = new LinearRegression(gamma = 0.000001, n_iter = 50000, n_MSE_show = 10)
    lr.fit(train_X, train_y)
    println("Regression coefficients:")
    lr.weights.map(x => print(s"${x} "))
    println()
    println("Regression intercept:")
    println(lr.intercept)

    println(s"Train: MSE = ${lr.MSE(train_X, train_y)}")
    println(s"Test: MSE = ${lr.MSE(test_X, test_y)}")
}}
