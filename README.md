Video Coding is the process of encoding information in a video using fewer bits than the original representation. This is achieved by Motion Estimation. A video compression system that uses Block Matching Algorithm (BMA) and compares an incoming block in the reference frame with a candidate block in the current frame is efficient and gives better results in terms of speed and entropy. This block-based technique when combined with the Full Search Algorithm (FSA) also helps in getting an accurate prediction quality.But this combined strategy functions sequentially, thereby taking enormous time to generate the output. This gives rise to the need for multiple agents that function simultaneously. Furthermore, this framework is made more configurable dynamically. Hence, the proposed system delivers a Speed Improvement Rate (SIR) of 44% on average that is reasonably lesser than the time taken by the existing system.

In Motion Estimation, we take two frames from the video chosen for the process of compression. The first frame is called the previous frame or the reference frame. The second frame is called the current frame that is going to be regenerated using the data in the previous frame. We divide the two frames into macro-blocks of size 4x4 or any required size with respect to the size of the frame. Now we compare the current frame with the previous frame and compute the motion vector for each block in the previous frame correspondingly. The location of the macro block where it moves in the current frame will give the motion vector.To compare the blocks in the current frame with that of the blocks in the reference frame and to compute the motion vectors, SAD (Sum Absolute Difference) is used. After comparison and computation of SAD and the final motion vector values of each block, each pixel value in the current block that is to be plotted in the current frame is computed.   
               
This process of computing motion vectors and the pixels in the current frame using full search takes a lot amount of time. To perform this task faster, dynamic multiple pixel agents are employed after predicting the nature of the video using a frame agent.

After prediction of each pixel in the current frame, the frame is regenerated and its prediction quality is compared with that of the original current frame extracted from the video. The actually regenerated frame is expected to give a better prediction quality than the original frame.

An accurate computation of Motion Vectors for successive frames is done for a data set of 5 videos, having a varied diversity of duration, format and nature: duration- 30 seconds to 5 minutes, format- MPG, AVI, MP4, nature- indoor and outdoor. The computational complexity of the proposed system and existing system is compared and the time taken for completion of proposed system is calculated. An increase in the Speed Improvement Rate (SIR) of the proposed system is achieved. On an average, 44% SIR is computed. The reconfigurable framework contributes to optimal use of resources needed for each instance of input video, thereby increasing computational efficiency.
