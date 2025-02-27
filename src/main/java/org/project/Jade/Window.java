package org.project.Jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.project.Util.Time;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private static Window window = null;
    private static Scene currentScene;
    private int width,height;
    private String title;
    private Long glfwWindow;

    public float r,g,b,a;

    private Window(){
        this.width=1920;
        this.height=1080;
        this.title="Game Engine";
        this.r=1;
        this.g=1;
        this.b=1;
        this.a=1;
    }

    public static void changeScene(int newScene){
        switch(newScene){
            case 0:
                currentScene = new LevelEditorScene();
                //currentScene.init()
                break;
            case 1:
                currentScene = new LevelScene();
                break;
            default:
                assert false : "Unknown scene '" + newScene + "'";
                break;
        }
    }
    public static Window get(){
        if (Window.window==null){
            Window.window = new Window();
        }
        return Window.window;
    }

    public void run(){
        System.out.println("Hello " + Version.getVersion() +"!");
        init();
        loop();

        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);
        glfwTerminate();
        glfwSetErrorCallback(null).free();


    }

    public void init(){
        GLFWErrorCallback.createPrint(System.err).set();
        if(!glfwInit()){
            throw new IllegalStateException("Unable to initialize");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE,GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE , GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED,GLFW_TRUE);

        glfwWindow = glfwCreateWindow(this.width , this.height , this.title , NULL , NULL);
        if (glfwWindow == NULL){
            throw new IllegalStateException("Failed to create GLFW Window");
        }
        glfwSetCursorPosCallback(glfwWindow , MouseListener::mousePosCallback );
        glfwSetMouseButtonCallback(glfwWindow , MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow,MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow , KeyListener::keyCallBack);
        glfwMakeContextCurrent(glfwWindow);
        glfwSwapInterval(1);

        glfwShowWindow(glfwWindow);

        GL.createCapabilities();

        Window.changeScene(0);
    }

    public void loop(){
float beginTime = Time.getTime();
float endTime;
float dt = -1f;

        while (!glfwWindowShouldClose(glfwWindow)){
            glfwPollEvents();

            glClearColor(r,g,b,a);
            glClear(GL_COLOR_BUFFER_BIT);

            if (dt >= 0){
            currentScene.update(dt);}


            glfwSwapBuffers(glfwWindow);
            endTime = Time.getTime();
            dt = endTime - beginTime;
            beginTime=endTime;
        }
    }
}
