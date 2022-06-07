import tensorflow as tf
from tensorflow.keras.preprocessing import image

import numpy as np 

class PicturePrediction:
  model= tf.keras.models.load_model( "/content/saved_model2")
  labels_path= "/content/labels (4).txt"
  def __init__(self, image_path):
    self.image_path= image_path

  def load_image(self):
    img = image.load_img(self.image_path,target_size=(224,224))
    img_array = image.img_to_array(img)/255.0
    img_batch = np.expand_dims(img_array,axis=0)

    return img_batch 

  def labels(self):
    return np.array(open(self.labels_path).read().splitlines())

  def image_prediction(self):
    image = self.load_image()
    label = self.labels()
    prediction = self.model.predict(image)
    prediction_array = np.argmax(prediction)

    prediction_label = label[prediction_array]
    confident1 = prediction[0][prediction_array]
    
    return "terdeteksi penyakit {} dengan tingkat kepercayaan {}".format(prediction_label,confident1)