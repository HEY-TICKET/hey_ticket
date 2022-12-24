import React, { ReactElement, useState } from "react";
import { NextPageWithLayout } from "./_app";
import Input from "../components/Input";
import Link from "next/link";

interface SignInProps {}

const SignUp: NextPageWithLayout = ({}: SignInProps) => {
  const [id, setId] = useState("");
  const [password, setPassword] = useState("");
  const [confirm, setConfirm] = useState("");

  return (
    <div className={"flex flex-col items-center gap-3 w-full"}>
      <h3
        className={
          "text-4xl font-bold text-black w-full flex justify-center mb-8"
        }
      >
        Sign up
      </h3>
      <Input
        placeholder={"아이디를 입력해주세요."}
        value={id}
        onChange={(e) => setId(e.target.value)}
      />
      <Input
        placeholder={"비밀번호를 입력해주세요."}
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <Input
        placeholder={"비밀번호를 입력해주세요."}
        value={confirm}
        onChange={(e) => setConfirm(e.target.value)}
      />

      <button
        className={
          "bg-jin-blue-400 hover:bg-jin-blue-500 w-48 rounded-full text-white p-4 font-bold mt-4"
        }
      >
        SIGN UP
      </button>
      {/*<button className={"w-full p-4 border border-gray-500 "}>*/}
      {/*  회원이 아니신가요? 가입하기*/}
      {/*</button>*/}
      {/*todo : 추후 업데이트 기능*/}
      {/*<div className={"mt-8"}>*/}
      {/*  <span>소셜 계정으로 로그인</span>*/}
      {/*</div>*/}
    </div>
  );
};

SignUp.getLayout = function getLayout(page: ReactElement) {
  return (
    <div className={"h-screen flex items-center justify-center bg-gray-100"}>
      <section
        className={
          "grid grid-cols-2 border shadow-2xl rounded-2xl w-[60%] h-[70%] max-w-[1200px] max-h-[800px]"
        }
      >
        <div className={"bg-jin-blue-400 rounded-l-2xl flex items-center"}>
          <div
            className={
              "flex flex-col items-center justify-center gap-3 w-full gap-6"
            }
          >
            <h3
              className={
                "text-4xl font-bold text-white w-full flex justify-center mb-8"
              }
            >
              Welcome, Back!
            </h3>
            <p className={"text-white text-sm"}>
              To keep connected with us please login with your personal info
            </p>

            <Link href={"/signIn"}>
              <button
                className={
                  "w-48 rounded-full text-white p-4 font-bold border border-white hover:bg-jin-blue-500"
                }
              >
                SIGN IN
              </button>
            </Link>
          </div>
        </div>

        <div
          className={
            "grid-span-1 border-l p-8 flex items-center bg-white rounded-r-2xl"
          }
        >
          {page}
        </div>
      </section>
    </div>
  );
};

export default SignUp;
